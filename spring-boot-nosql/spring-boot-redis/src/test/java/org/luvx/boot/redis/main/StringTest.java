package org.luvx.boot.redis.main;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.luvx.common.util.PrintUtils.println;

public class StringTest extends ApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void m0() {
        // stringRedisTemplate.opsForValue().set();
    }

    @Test
    void m1() {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        // redisTemplate.opsForValue().set("haha", "haha1");
        Object haha = operations.get("haha");
        println(haha);
    }
}
