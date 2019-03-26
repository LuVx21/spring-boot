package org.luvx.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: org.luvx.common.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/26 12:32
 */
@Configuration
public class RedisConfig {
    // @Bean
    // public JedisCluster JedisClusterFactory() {
    //     Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
    //     jedisClusterNodes.add(new HostAndPort("192.168.5.112", 4564));
    //     JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
    //     return jedisCluster;
    // }
    //
    // @Bean
    // public Jedis JedisFactory() {
    //     Jedis jedis = new Jedis("192.168.5.112", 6379);
    //     jedis.auth("root");
    //     return jedis;
    // }
}
