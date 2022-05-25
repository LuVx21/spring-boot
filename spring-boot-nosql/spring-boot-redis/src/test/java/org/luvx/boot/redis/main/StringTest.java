package org.luvx.boot.redis.main;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.data.redis.core.ValueOperations;

import static org.luvx.common.util.PrintUtils.println;

public class StringTest extends ApplicationTests {
    @Test
    void m0() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String key = "s-key";
        operations.set(key, "haha1");
        Object haha = operations.get(key);
        println(haha);
    }

    @Test
    void m1() {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        String key = "s-key-1";
        operations.set(key, "haha1".getBytes());
        Object o = operations.get(key);
        if (o instanceof byte[]) {
            String s = new String((byte[]) o);
            println(s);
        }
    }
}