package org.luvx.boot.redis.main;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.data.redis.core.RedisTemplate;

public class ListTest extends ApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void m1() {
        // API.println("是 null", redisTemplate == null);
        redisTemplate.opsForList();
    }
}
