package org.luvx.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setTest() {
        stringRedisTemplate.opsForValue().set("foo", "bar");
        Assert.assertEquals("bar", stringRedisTemplate.opsForValue().get("foo"));
    }

    @Test
    public void userTest() throws Exception {
        User user = new User("LuVx", "1121", 24);
        ValueOperations<String, User> operations = redisTemplate.opsForValue();

        operations.set("Ren", user);
        operations.set("LuVx", user, 1, TimeUnit.SECONDS);

        Thread.sleep(1000);

        boolean exists = redisTemplate.hasKey("Ren");
        System.out.println(exists);
    }
}