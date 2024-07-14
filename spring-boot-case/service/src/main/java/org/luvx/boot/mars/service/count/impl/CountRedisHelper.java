package org.luvx.boot.mars.service.count.impl;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.luvx.boot.mars.grpc.sdk.CountType;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CountRedisHelper {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Map<Long, Map<Integer, Integer>> getByCountIds(Collection<Long> countIds) {
        List<Object> objects = redisTemplate.executePipelined((RedisCallback<Map<byte[], byte[]>>) conn -> {
                    RedisHashCommands redisHashCommands = conn.hashCommands();
                    for (long id : countIds) {
                        redisHashCommands.hGetAll(String.valueOf(id).getBytes());
                    }
                    return null;
                }
        );

        Map<Long, Map<Integer, Integer>> result = Maps.newHashMapWithExpectedSize(countIds.size());
        Iterator<Long> it = countIds.iterator();
        int index = 0;
        while (it.hasNext()) {
            Object o = objects.get(index++);
            Map<Integer, Integer> map1 = o == null ? Collections.emptyMap() : ((Map<?, ?>) o).entrySet().stream()
                    .collect(Collectors.toMap(e -> NumberUtils.toInt(e.getKey().toString()), e -> NumberUtils.toInt(e.getValue().toString())));
            result.put(it.next(), map1);
        }
        return result;
    }

    public Map<Long, Integer> getByType(Collection<Long> countIds, int type) {
        List<Object> objects = redisTemplate.executePipelined((RedisCallback<Map<byte[], byte[]>>) conn -> {
                    RedisHashCommands redisHashCommands = conn.hashCommands();
                    for (long id : countIds) {
                        redisHashCommands.hGet(String.valueOf(id).getBytes(), String.valueOf(type).getBytes());
                    }
                    return null;
                }
        );

        Map<Long, Integer> result = Maps.newHashMapWithExpectedSize(countIds.size());
        Iterator<Long> it = countIds.iterator();
        int index = 0;
        while (it.hasNext()) {
            Long next = it.next();
            Object o = objects.get(index++);
            if (o != null) {
                result.put(next, NumberUtils.toInt(o.toString()));
            }
        }
        return result;
    }


    public void remove(long countId) {
        redisTemplate.delete(String.valueOf(countId));
    }

    public void setValue(long countId, Collection<CountType> types, int value) {
        Map<Integer, Integer> map = types.stream()
                .map(CountType::getType)
                .collect(Collectors.toMap(Function.identity(), _ -> value));
        redisTemplate.opsForHash().putAll(String.valueOf(countId), map);
    }
}
