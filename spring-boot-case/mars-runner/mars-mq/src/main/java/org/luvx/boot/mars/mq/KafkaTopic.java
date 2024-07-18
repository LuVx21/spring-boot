package org.luvx.boot.mars.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.luvx.coding.common.enums.EnumHasCode;

@Getter
@AllArgsConstructor
public enum KafkaTopic implements EnumHasCode<String> {
    COUNT(KafkaTopics.topic_count, "计数服务Topic"),
    ;

    private final String topic;
    private final String name;

    @Override
    public String getCode() {
        return topic;
    }
}
