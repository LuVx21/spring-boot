package org.luvx.boot.redis.main;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.luvx.common.util.PrintUtils.println;

public class HashTest extends ApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void m1() {
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        // println(haha.getClass().getName(), haha);
    }
}
