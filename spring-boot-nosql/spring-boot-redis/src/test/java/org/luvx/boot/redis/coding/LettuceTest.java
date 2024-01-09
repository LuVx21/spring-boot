package org.luvx.boot.redis.coding;

import io.lettuce.core.RedisClient;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.entity.User;
import org.luvx.boot.redis.other.SerializeUtil;
import org.luvx.boot.redis.other.StringByteCodec;

class LettuceTest {

    RedisClient redisClient = RedisClient.create("");

    @Test
    void m0() {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisCommands<String, String> redisCommands = connection.sync();
            SetArgs setArgs = SetArgs.Builder.nx().ex(5);

            final String key = "name-0";
            String result = redisCommands.set(key, "throwable", setArgs);
            System.out.println(result);
            result = redisCommands.get(key);
            System.out.println(result);
        }
    }

    @Test
    void m1() {
        try (StatefulRedisConnection<String, byte[]> connect = redisClient.connect(StringByteCodec.INSTANCE)) {
            RedisCommands<String, byte[]> cmd = connect.sync();

            cmd.set("", "1".getBytes());
            System.out.println(new String(cmd.get("")));

            final String key = "name-1";
            User u = User.of(1, "foo-bar");
            cmd.set(key, SerializeUtil.serialize(u));

            Object name = SerializeUtil.deserialize(cmd.get(key));
            System.out.println(name);
        }
    }
}
