package org.luvx._new.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName: org.luvx._new.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 14:24
 */
@Getter
@Setter
@ConfigurationProperties("kafka.topic")
public class KafkaTopicProperties {
    private String[] topicName;
}
