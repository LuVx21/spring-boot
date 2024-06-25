package org.luvx.boot.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory factory) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setEnableDefaultSerializer(false);

        RedisSerializer<?> json = serializer();

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(json);
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(json);
        template.afterPropertiesSet();
        return template;
    }

    private RedisSerializer<?> serializer() {
        RedisSerializer<?> serializer = RedisSerializer.json();
        // 使用protobuf
        // serializer = new ProtobufRedisSerializer();

        // serializer = new GenericToStringSerializer<>(Object.class);

        // ObjectMapper om = new ObjectMapper();
        // om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        // om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // om.registerModule(new Jdk8Module())
        //         .registerModule(new JavaTimeModule())
        //         .registerModule(new ParameterNamesModule());

        // serializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
        // serializer = new GenericJackson2JsonRedisSerializer(om);

        return serializer;
    }
}
