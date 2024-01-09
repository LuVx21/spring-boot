package org.luvx.boot.redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisAppTests {
    @Resource
    protected RedisTemplate<String, Object> redisTemplate;
    @Resource
    protected StringRedisTemplate           stringRedisTemplate;

    @Resource(name = "redisTemplate")
    protected ValueOperations<String, Object>        valueOps;
    @Resource(name = "redisTemplate")
    protected HashOperations<String, Object, Object> hashOps;
    @Resource(name = "redisTemplate")
    protected ListOperations<String, Object>         listOps;
    @Resource(name = "redisTemplate")
    protected SetOperations<String, Object>          setOps;
    @Resource(name = "redisTemplate")
    protected ZSetOperations<String, Object>         zSetOps;

    @Resource(name = "redisTemplate")
    protected GeoOperations<String, Object> geoOps;

    // protected ClusterOperations<String, Object>     clusterOps;
    // protected HyperLogLogOperations<String, Object> hyperLogLogOps;
    // protected StreamOperations<String, Object, Object> objectStreamOps;

    @Resource
    protected RedissonClient redissonClient;

    @Test
    void inti() {
    }
}
