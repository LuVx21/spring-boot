package org.luvx.boot.redis.main;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.RedisAppTests;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.luvx.coding.common.more.MorePrints.println;

public class ZSetTest extends RedisAppTests {
    @Test
    void m0() {
        ZSetOperations<String, String> zSetOps = stringRedisTemplate.opsForZSet();
        String key = "zset-key";
        Set<TypedTuple<String>> values = IntStream.range(0, 21)
                .mapToObj(i -> new DefaultTypedTuple<>(Character.toString('a' + i), i + 1.0))
                .collect(Collectors.toSet());
        zSetOps.add(key, values);

        Set<String> set = zSetOps.range(key, 0, 10);
        println(set);
        set = zSetOps.rangeByScore(key, 0.0, 15.0);
        println(set);
    }

    @Test
    void m1() {
        String key = "zset-key-1";
        Set<TypedTuple<Object>> values = IntStream.range(0, 21)
                .mapToObj(i -> {
                    return new DefaultTypedTuple<>((Object) Character.toString('a' + i), i + 1.0);
                })
                .collect(Collectors.toSet());
        zSetOps.add(key, values);

        Set<Object> set = zSetOps.range(key, 0, 10);
        println(set);
        set = zSetOps.rangeByScore(key, 0.0, 15.0);
        println(set);
    }
}
