package org.luvx.boot.mars.service.count.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mars.dao.redis.RedisKeyPrefix;
import org.luvx.boot.mars.rpc.common.count.CountType;
import org.luvx.boot.nosql.redis.accessor.RedisHashAccessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CountRedisHelper implements RedisHashAccessor<Long, Integer, Integer> {
    @Lazy
    @Resource
    private CountService                  countService;
    @Getter
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Map<Long, Map<Integer, Integer>> getByCountIds(Collection<Long> countIds) {
        Map<Long, Map<Integer, Integer>> result = getByKeys(countIds);
        result.forEach((k, m) -> {
            if (m.values().stream().anyMatch(i -> i < 0)) {
                // 清洗
            }
        });
        return result;
    }

    public Map<Long, Integer> getByType(Collection<Long> countIds, CountType type) {
        Map<Long, Map<Integer, Integer>> m = getByHashKeys(countIds, Collections.singletonList(type.getType()));
        return m.entrySet().stream()
                .filter(e -> e.getValue().containsKey(type.getType()))
                .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().get(type.getType())));
    }

    public void remove(long countId) {
        redisTemplate.delete(redisKey(countId));
    }

    public void setValue(long countId, Collection<CountType> types, int value) {
        Map<Integer, Integer> map = types.stream()
                .map(CountType::getType)
                .collect(Collectors.toMap(e -> e, _ -> value));
        set(countId, map);
    }

    @Override
    public String redisKey(Long key) {
        return RedisKeyPrefix.COUNT.redisKey(key);
    }

    @Override
    public Map<Long, Map<Integer, Integer>> loadDataFromDb(Collection<Long> ids) {
        return countService.getByIdsFromDB(ids);
    }

    @Override
    public Map<Long, Map<Integer, Integer>> loadDataFromDb(Collection<Long> ids, Collection<Integer> hks) {
        return countService.getByIdsFromDB(ids, hks);
    }
}
