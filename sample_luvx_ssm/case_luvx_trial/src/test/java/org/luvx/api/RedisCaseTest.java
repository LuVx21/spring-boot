package org.luvx.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.luvx.Serialize.ListTranscoder;
import org.luvx.Serialize.ObjectTranscoder;
import org.luvx.entity.User;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化 反序列化 Redis
 */
public class RedisCaseTest {

    User user1 = new User("Xie", "1121", 19);
    User user2 = new User("Ren", "1234", 20);
    List<User> list = new ArrayList<User>();
    Jedis jedis = new Jedis();

    /**
     * 序列化对象
     */
    @Test
    public void objTest() {
        ObjectTranscoder<User> objectTranscoder = new ObjectTranscoder<User>();
        // 序列化
        byte[] key = (user1.hashCode() + "").getBytes();
        byte[] value = objectTranscoder.serialize(user1);
        jedis.set(key, value);
        // 反序列化
        byte[] result = jedis.get(key);
        User user = objectTranscoder.deserialize(result);
        System.out.println(user);
    }

    /**
     * 序列化集合
     */
    @Test
    public void colTest() throws IOException {
        list.add(user1);
        list.add(user2);
        ListTranscoder<User> listTranscoder = new ListTranscoder<User>();

        // 序列化
        byte[] key = (list.hashCode() + "").getBytes();
        byte[] value = listTranscoder.serialize(list);
        jedis.set(key, value);

        // 反序列化
        byte[] result = jedis.get(key);
        List<User> newlist = listTranscoder.deserialize(result);
        for (User u : newlist) {
            System.out.println(u);
        }
    }

    @After
    public void end() {
        jedis.close();
    }
}