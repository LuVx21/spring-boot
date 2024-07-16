package org.luvx.boot.mars.service.count.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.luvx.boot.mars.rpc.sdk.CountType;
import org.luvx.boot.nosql.redis.RedisHashAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CountRedisHelper implements RedisHashAccessor<Long> {
    @Getter
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Map<Long, Map<Integer, Integer>> getByCountIds(Collection<Long> countIds) {
        Map<Long, Map<Object, Object>> byKeys = getByKeys(countIds);
        Map<Long, Map<Integer, Integer>> result = byKeys.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    return e.getValue().entrySet().stream()
                            .collect(Collectors.toMap(ee -> NumberUtils.toInt(ee.getKey().toString()), ee -> NumberUtils.toInt(ee.getValue().toString())));
                }));
        result.forEach((k, m) -> {
            if (m.values().stream().anyMatch(i -> i < 0)) {
                // 清洗
            }
        });
        return result;
    }

    public Map<Long, Integer> getByType(Collection<Long> countIds, CountType type) {
        return getByHashKeys(countIds, type.getType()).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> NumberUtils.toInt(e.getValue().toString())));
    }

    public void remove(long countId) {
        redisTemplate.delete(redisKey(countId));
    }

    public void setValue(long countId, Collection<CountType> types, int value) {
        Map<String, Integer> map = types.stream()
                .map(CountType::getType)
                .collect(Collectors.toMap(String::valueOf, _ -> value));
        redisTemplate.opsForHash().putAll(redisKey(countId), map);
    }

    @Override
    public String redisKey(Long key) {
        return "c:" + key;
    }
}
