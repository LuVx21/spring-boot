package org.luvx.boot.redis.main;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.ApplicationTests;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

import static org.luvx.coding.common.more.MorePrints.println;

@Slf4j
public class HashTest extends ApplicationTests {
    @Test
    void m0() {
        HashOperations<String, Object, Object> operations = stringRedisTemplate.opsForHash();
        String key = "hash-key";
        operations.putAll(key, Map.of(
                "a", "aa",
                "b", "bb",
                "c", "cc"
        ));
        List<Object> list = operations.multiGet(key, List.of("a", "b"));
        log.info("list:{}", list);
    }

    @Test
    void m1() {
        HashOperations<String, Object, Object> operations = redisTemplate.opsForHash();
    }
}
