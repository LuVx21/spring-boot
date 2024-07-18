package org.luvx.boot.mars.service.count.impl;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mars.dao.entity.Count;
import org.luvx.boot.mars.dao.mapper.CountMapper;
import org.luvx.boot.mars.rpc.common.count.CountOperateType;
import org.luvx.boot.mars.rpc.common.count.CountType;
import org.luvx.boot.mars.service.count.CountEvent;
import org.luvx.boot.mars.service.count.CountEvent.CountEventData;
import org.luvx.boot.mars.service.kafka.KafkaTopics;
import org.luvx.coding.infra.retrieve.RetrieveIdUtils;
import org.luvx.coding.infra.retrieve.base.MultiDataRetrievable;
import org.luvx.coding.infra.retrieve.retriever.CacheBasedDataRetriever;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toMap;
import static org.luvx.coding.common.function.MoreFunctions.runCatching;

@Slf4j
@Service
public class CountService {
    private final CacheBasedDataRetriever<Long, Map<Integer, Integer>> requestCache = CacheBasedDataRetriever.of(null);

    @Resource
    private CountMapper                       countMapper;
    @Lazy
    @Resource
    private CountRedisHelper                  countRedisHelper;
    @Resource
    private JdbcClient                        jdbcClient;
    @Resource
    private KafkaTemplate<String, CountEvent> kafkaTemplate;

    public Map<Long, Map<Integer, Integer>> getByIdsFromDB(Collection<Long> ids) {
        List<Count> list = countMapper.wrapper()
                .in(Count::getCountId, ids)
                .list();
        return list2Map(list);
    }

    public Map<Long, Map<Integer, Integer>> getByIdsFromDB(Collection<Long> ids, Collection<Integer> types) {
        List<Count> list = countMapper.wrapper()
                .in(Count::getCountId, ids)
                .in(Count::getCountType, types)
                .list();
        return list2Map(list);
    }

    public Map<Long, Map<Integer, Integer>> list2Map(List<Count> list) {
        Map<Long, Map<Integer, Integer>> result = Maps.newHashMapWithExpectedSize(list.size());
        for (Count count : list) {
            long countId = count.getCountId();
            Map<Integer, Integer> m = result.computeIfAbsent(countId, _ -> Maps.newHashMap());
            m.put(count.getCountType(), count.getCountValue());
        }
        return result;
    }

    public Map<Long, Map<Integer, Integer>> getByIds(Collection<Long> ids) {
        var list = List.of(requestCache, new MultiDataRetrievable<Long, Map<Integer, Integer>>() {
            @Override
            public Map<Long, Map<Integer, Integer>> get(Collection<Long> _ids) {
                return countRedisHelper.getByCountIds(_ids);
            }
        });
        return RetrieveIdUtils.get(ids, list);
    }

    public Map<Long, Integer> getByType(CountType type, Collection<Long> countIds) {
        return countRedisHelper.getByType(countIds, type).entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void operate(CountOperateType event, long countId, Collection<CountType> types, int value) {
        if (value < 0 || countId <= 0) {
            return;
        }
        switch (event) {
            case DEC_COUNT, INC_COUNT:
                int v = event == CountOperateType.DEC_COUNT ? -value : value;
                types.forEach(type -> incr(countId, type, v));
                break;
            case SET_COUNT:
                save(countId, types, value);
                break;
            default:
        }
    }

    private void incr(long countId, CountType type, int delta) {
        runCatching(() -> delete(countId, type));
        countMapper.incr(countId, type.getType(), delta);
        delete(countId, type);
    }

    private void delete(long countId, CountType type) {
        countRedisHelper.delete(countId, type.getType());
        requestCache.getCache().invalidate(countId);
    }

    private void save(long countId, Collection<CountType> types, int value) {
        if (value == 0) {
            // 不存在的没必要插入数据
            Count count = new Count();
            count.setCountId(countId);
            count.setCountValue(value);
            count.setUpdateTime(currentTimeMillis());
            for (CountType type : types) {
                count.setCountType(type.getType());
                countMapper.updateByPrimaryKey(count);
            }
        } else {
            long time = currentTimeMillis();
            for (CountType type : types) {
                insertOrUpdate(countId, type, value, time);
            }
        }
        countRedisHelper.setValue(countId, types, value);
        requestCache.getCache().invalidate(countId);
    }

    public void asyncOperate(CountOperateType event, long countId, Collection<CountType> types, int value) {
        if (value < 0 || countId <= 0) {
            return;
        }
        switch (event) {
            case DEC_COUNT, INC_COUNT:
                int v = event == CountOperateType.DEC_COUNT ? -value : value;
                types.forEach(type -> asyncDelta(event, countId, type, v));
                break;
            case SET_COUNT:
                remoteSave(countId, types, value);
                break;
            default:
        }
    }

    private void asyncDelta(CountOperateType operateType, long countId, CountType type, int delta) {
        CountEventData data = new CountEventData(countId, type, delta);
        kafkaTemplate.send(KafkaTopics.topic_count, countId + "", new CountEvent(operateType, data));

        requestCache.getCache().invalidate(countId);
    }

    private void remoteSave(long countId, Collection<CountType> types, int value) {
        types.forEach(type -> {
            CountEventData data = new CountEventData(countId, type, value);
            kafkaTemplate.send(KafkaTopics.topic_count, countId + "", new CountEvent(CountOperateType.SET_COUNT, data));
        });
        countRedisHelper.setValue(countId, types, value);
        requestCache.getCache().invalidate(countId);
    }

    public void batchSet(long countId, Map<CountType, Integer> countMap) {
        long time = currentTimeMillis();
        for (Map.Entry<CountType, Integer> entry : countMap.entrySet()) {
            CountType key = entry.getKey();
            Integer value = entry.getValue();
            insertOrUpdate(countId, key, value, time);
        }

        requestCache.getCache().invalidate(countId);
    }

    private void insertOrUpdate(long countId, CountType key, Integer value, long time) {
        String sql = """
                insert into count (count_id, count_type, count_value, update_time)
                values(?, ?, ?, ?)
                on duplicate key update count_value=?, update_time=?
                """;
        jdbcClient.sql(sql)
                .params(countId, key.getType(), value, time, value, time)
                .update();
    }
}
