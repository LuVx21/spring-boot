package org.luvx.boot.redis.main;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Slf4j
public class ListTest extends ApplicationTests {
    @Test
    void m0() {
        ListOperations<String, String> operations = stringRedisTemplate.opsForList();
        String key = "list-key";
        operations.leftPush(key, "a");
        operations.leftPush(key, "b");
        operations.set(key, 0, "c");
        List<String> list = operations.rightPop(key, 10);
        // [a, c]
        log.info("list:{}", list);
    }

    @Test
    void m1() {
        ListOperations<String, Object> operations = redisTemplate.opsForList();
    }
}
