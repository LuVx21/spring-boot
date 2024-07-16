package org.luvx.kafka.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class KafkaTopicConfig {
    private final KafkaTopicProperties properties;

    @Bean
    public String[] kafkaTopicName() {
        return properties.getTopicName();
    }
}
