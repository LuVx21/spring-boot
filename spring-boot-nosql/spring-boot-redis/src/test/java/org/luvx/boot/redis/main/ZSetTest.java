package org.luvx.boot.redis.main;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.Set;

import static org.luvx.coding.common.more.MorePrints.println;

public class ZSetTest extends ApplicationTests {
    @Test
    void m0() {
        ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();
        String key = "zset-key";
        operations.add(key, Set.of(
                new DefaultTypedTuple("a", 1.0),
                new DefaultTypedTuple("b", 99.0),
                new DefaultTypedTuple("c", 101.0)
        ));
        Set<String> set = operations.range(key, 0, 100);
        println(set);
        set = operations.rangeByScore(key, 0.0, 100.0);
        println(set);
    }

    @Test
    void m1() {
        SetOperations<String, Object> operations = redisTemplate.opsForSet();
    }
}
