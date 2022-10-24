package org.luvx.boot.redis.main;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.data.redis.core.SetOperations;

import java.util.Set;

import static org.luvx.coding.common.more.MorePrints.println;

public class SetTest extends ApplicationTests {
    @Test
    void m0() {
        SetOperations<String, String> operations = stringRedisTemplate.opsForSet();
        String key = "set-key";
        operations.add(key, "a", "b");
        Set<String> members = operations.members(key);
        println(members);
    }

    @Test
    void m1() {
        SetOperations<String, Object> operations = redisTemplate.opsForSet();
    }
}
