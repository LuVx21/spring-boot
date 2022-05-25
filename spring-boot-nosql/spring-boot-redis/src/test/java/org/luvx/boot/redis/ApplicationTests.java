package org.luvx.boot.redis;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApplicationTests {
    @Resource
    protected RedisTemplate<String, Object> redisTemplate;
    @Resource
    protected StringRedisTemplate           stringRedisTemplate;
}
