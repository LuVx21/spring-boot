package org.luvx.boot.mars.service.count.impl;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mars.grpc.sdk.CountType;
import org.luvx.boot.mars.grpc.sdk.count.CountEvent;
import org.luvx.boot.mars.service.count.CountService;
import org.luvx.boot.tools.dao.entity.Count;
import org.luvx.boot.tools.dao.mapper.CountMapper;
import org.luvx.coding.infra.retrieve.RetrieveIdUtils;
import org.luvx.coding.infra.retrieve.base.MultiDataRetrievable;
import org.luvx.coding.infra.retrieve.retriever.CacheBasedDataRetriever;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class CountServiceImpl implements CountService {
    private final CacheBasedDataRetriever<Long, Map<Integer, Integer>> requestCache = CacheBasedDataRetriever.of(null);

    @Resource
    private CountMapper      countMapper;
    @Resource
    private CountRedisHelper countRedisHelper;
    @Resource
    private JdbcClient       jdbcClient;

    @Override
    public Map<Long, Map<Integer, Integer>> getByIds(Collection<Long> ids) {
        List<MultiDataRetrievable<Long, Map<Integer, Integer>>> list = List.of(requestCache, new MultiDataRetrievable<>() {
            @Override
            public Map<Long, Map<Integer, Integer>> get(Collection<Long> _ids) {
                return countRedisHelper.getByCountIds(_ids);
            }
        });
        return RetrieveIdUtils.get(ids, list);
    }

    @Override
    public Map<Long, Integer> getByType(CountType type, Collection<Long> countIds) {
        return countRedisHelper.getByType(countIds, type.getType()).entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void operate(CountEvent event, long countId, Collection<CountType> types, int value) {
        if (value < 0 || countId <= 0) {
            return;
        }
        switch (event) {
            case DEC_COUNT:
                types.forEach(type -> incr(countId, type, -value));
                break;
            case INC_COUNT:
                types.forEach(type -> incr(countId, type, value));
                break;
            case SET_COUNT:
                save(countId, types, value);
                break;
            default:
        }
    }

    private void incr(long countId, CountType type, int delta) {
        countMapper.incr(countId, type.getType(), delta);
        requestCache.getCache().invalidate(countId);
    }

    @Override
    public void asyncOperate(CountEvent event, long countId, Collection<CountType> types, int value) {
        if (value < 0 || countId <= 0) {
            return;
        }
        switch (event) {
            case SET_COUNT:
                remoteSave(countId, types, value);
                break;
            case INC_COUNT:
                types.forEach(type -> asyncDelta(countId, type, value));
                break;
            case DEC_COUNT:
                types.forEach(type -> asyncDelta(countId, type, -value));
                break;
            default:
        }
    }

    private void asyncDelta(long countId, CountType type, int delta) {
        // TODO MQ
        requestCache.getCache().invalidate(countId);
    }


    private void remoteSave(long countId, Collection<CountType> types, int value) {
        // TODO MQ
        // 必须要hash到指定的consumer
        countRedisHelper.setValue(countId, types, value);
        requestCache.getCache().invalidate(countId);
    }

    private void save(long countId, Collection<CountType> types, int value) {
        if (value == 0) {
            long time = currentTimeMillis();
            for (CountType type : types) {
                Count count = new Count();
                count.setCountId(countId);
                count.setCountType(type.getType());
                count.setCountValue(value);
                count.setUpdateTime(time);
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

    @Override
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
