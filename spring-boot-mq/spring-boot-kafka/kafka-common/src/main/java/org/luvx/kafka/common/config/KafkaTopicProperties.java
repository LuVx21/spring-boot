package org.luvx.kafka.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("kafka.topic")
public class KafkaTopicProperties {
    private String[] topicName;
}
