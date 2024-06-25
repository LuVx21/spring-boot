package org.luvx.boot.redis.main;

import org.junit.jupiter.api.Test;
import org.luvx.boot.redis.RedisAppTests;
import org.luvx.boot.redis.entity.User;
import org.luvx.boot.redis.protobuf.UsersProto;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.data.redis.core.ValueOperations;

import static org.luvx.coding.common.more.MorePrints.println;

public class StringTest extends RedisAppTests {
    @Test
    void m0() {
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        String key = "s-key";
        valueOps.set(key, "haha1");
        Object haha = valueOps.get(key);
        println(haha);
    }

    @Test
    void m1() {
        String key = "s-key-1";
        valueOps.set(key, "haha1");
        Object o = valueOps.get(key);
        println(o);
    }

    @Test
    void m2() {
        String key = "increment-key";
        valueOps.set(key, 1);
        Object value = valueOps.get(key);
        MorePrints.println(value, value.getClass());

        Long increment = valueOps.increment(key, 1);
        System.out.println(increment);
    }

    @Test
    void m11() {
        String key = "s-key-2";
        User u = User.of(1, "foo-bar");
        valueOps.set(key, u);
        Object o = valueOps.get(key);
        println(o);
    }

    /**
     * https://lequ7.com/Redis-shi-yong-jin-jie.html
     */
    @Test
    void testStringRedisTemplateSimple() {
        stringRedisTemplate.opsForValue().set("mykey", "myvalue");
        MorePrints.println(
                stringRedisTemplate.opsForValue().get("mykey"),
                stringRedisTemplate.keys("my*"),
                stringRedisTemplate.hasKey("mykey"),
                stringRedisTemplate.delete("mykey"),
                stringRedisTemplate.opsForValue().get("mykey")
        );
    }

    @Test
    void testProto() {
        final String key = "s-key-proto";

        UsersProto.User.Builder u1 = UsersProto.User.newBuilder().setId("100").setName("foo").setSex("男");
        UsersProto.Users u = UsersProto.Users.newBuilder().addUsers(u1).build();
        valueOps.set(key, u);

        UsersProto.Users o = (UsersProto.Users) valueOps.get(key);
        println(o, o.getClass().getName(), o.getUsers(0).getSex());
    }
}
