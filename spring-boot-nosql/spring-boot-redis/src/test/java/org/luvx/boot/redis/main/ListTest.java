package org.luvx.boot.redis.main;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.RedisAppTests;
import org.springframework.data.redis.core.ListOperations;

import java.util.List;

@Slf4j
public class ListTest extends RedisAppTests {
    @Test
    void m0() {
        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        String key = "list-key";
        for (int i = 0; i < 12; i++) {
            listOps.rightPush(key, Character.toString('a' + i));
        }

        listOps.set(key, 1, "bb");

        List<String> list = listOps.rightPop(key, 10);
        log.info("list:{}", list);
    }

    @Test
    void m1() {
        String key = "list-key-1";
        for (int i = 0; i < 12; i++) {
            listOps.rightPush(key, Character.toString('a' + i));
        }

        listOps.set(key, 1, "bb");
    }
}
