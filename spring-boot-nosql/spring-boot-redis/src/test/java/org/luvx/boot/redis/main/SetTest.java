package org.luvx.boot.redis.main;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.RedisAppTests;
import org.springframework.data.redis.core.SetOperations;

import java.util.Set;

import static org.luvx.coding.common.more.MorePrints.println;

public class SetTest extends RedisAppTests {
    @Test
    void m0() {
        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        String key = "set-key";
        setOps.add(key, "a", "b", "a", "c");
        Set<String> members = setOps.members(key);
        println(members);
    }

    @Test
    void m1() {
        String key = "set-key-1";
        setOps.add(key, "a", "b", "a", "c");
        Set<Object> members = setOps.members(key);
        println(members);
    }
}
