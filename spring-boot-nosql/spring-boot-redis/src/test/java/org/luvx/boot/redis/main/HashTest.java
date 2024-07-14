package org.luvx.boot.redis.main;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.RedisAppTests;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.data.redis.connection.RedisHashCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.luvx.boot.redis.utils.HashUtils.FLATTEN_MAPPER;

@Slf4j
public class HashTest extends RedisAppTests {
    @Test
    void m0() {
        HashOperations<String, Object, Object> hashOps = stringRedisTemplate.opsForHash();
        String key = "hash-key";
        hashOps.putAll(key, Map.of(
                "a", "aa",
                "b", "bb",
                "c", "cc"
        ));
        List<Object> list = hashOps.multiGet(key, List.of("a", "b"));
        log.info("list:{}", list);
    }

    @Test
    void m1() {
        String key = "hash-key-1";
        hashOps.putAll(key, Map.of(
                "a", "aa",
                "b", "bb",
                "c", "cc"
        ));
        List<Object> list = hashOps.multiGet(key, List.of("a", "b"));
        log.info("list:{}", list);
    }

    @Test
    void m2() {
        String key = "hash-key-2";
        // User u = User.of(1, "foo-bar");
        // u.setFriend(User.of(2, "222"));
        // hashOps.putAll(key, FLATTEN_MAPPER.toHash(u));

        hashOps.increment(key, "age", 18);

        Map<String, Object> map = hashOps.entries(key).entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));

        Object o = FLATTEN_MAPPER.fromHash(map);
        System.out.println(o);
    }

    @Test
    void m3() {
        // List<String> list = List.of("hash:101", "hash:102", "hash:103");
        byte[][] array = {"hash:101".getBytes(), "hash:102".getBytes(), "hash:103".getBytes()};
        List<Object> objects = redisTemplate.executePipelined((RedisCallback<Map<byte[], byte[]>>) conn -> {
                    RedisHashCommands redisHashCommands = conn.hashCommands();
                    for (byte[] bytes : array) {
                        // redisHashCommands.hGetAll(bytes);
                        redisHashCommands.hGet(bytes, "a".getBytes());
                    }
                    return null;
                }
        );
        MorePrints.println(objects, objects.getFirst().getClass());
    }
}
