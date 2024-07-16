// package org.luvx.boot.mars.dao.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.RedisSerializer;
//
// @Configuration
// public class RedisConfig {
//
//     @Bean
//     public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory factory) {
//         final RedisTemplate<String, Object> template = new RedisTemplate<>();
//         template.setConnectionFactory(factory);
//         template.setEnableDefaultSerializer(false);
//
//         RedisSerializer<Object> json = RedisSerializer.json();
//
//         template.setKeySerializer(RedisSerializer.string());
//         template.setValueSerializer(json);
//         template.setHashKeySerializer(RedisSerializer.string());
//         template.setHashValueSerializer(json);
//         template.afterPropertiesSet();
//         return template;
//     }
// }
