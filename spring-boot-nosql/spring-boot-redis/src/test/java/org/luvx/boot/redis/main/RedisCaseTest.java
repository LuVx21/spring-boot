package org.luvx.boot.redis.main;

import org.luvx.boot.redis.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 序列化 反序列化 Redis
 */
public class RedisCaseTest {

    User       user1 = new User(1, "Xie", "1121", 19);
    User       user2 = new User(2, "Ren", "1234", 20);
    List<User> list  = new ArrayList<User>();
    //    Jedis jedis = new Jedis();

    /**
     * 序列化对象
     */
    //    @Ignore
    //    @Test
    //    public void objTest() {
    //        ObjectTranscoder<User> objectTranscoder = new ObjectTranscoder<User>();
    //        // 序列化
    //        byte[] key = (user1.hashCode() + "").getBytes();
    //        byte[] value = objectTranscoder.serialize(user1);
    //        jedis.set(key, value);
    //        // 反序列化
    //        byte[] result = jedis.get(key);
    //        User user = objectTranscoder.deserialize(result);
    //        System.out.println(user);
    //    }
    //
    //    /**
    //     * 序列化集合
    //     */
    //    @Ignore
    //    @Test
    //    public void colTest() throws IOException {
    //        list.add(user1);
    //        list.add(user2);
    //        ListTranscoder<User> listTranscoder = new ListTranscoder<User>();
    //
    //        // 序列化
    //        byte[] key = (list.hashCode() + "").getBytes();
    //        byte[] value = listTranscoder.serialize(list);
    //        jedis.set(key, value);
    //
    //        // 反序列化
    //        byte[] result = jedis.get(key);
    //        List<User> newlist = listTranscoder.deserialize(result);
    //        for (User u : newlist) {
    //            System.out.println(u);
    //        }
    //    }
    //
    //    @After
    //    public void end() {
    //        jedis.close();
    //    }
}