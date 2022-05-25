package org.luvx.boot.redis.main;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.data.redis.core.RedisTemplate;

import io.vavr.API;

public class SetTest extends ApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void m1() {
        // API.println("是 null", redisTemplate == null);
        // redisTemplate.opsForSet().add("foo", "1", "2");
        Set<Object> foo = redisTemplate.opsForSet().members("foo");
        API.println(foo);
    }
}
