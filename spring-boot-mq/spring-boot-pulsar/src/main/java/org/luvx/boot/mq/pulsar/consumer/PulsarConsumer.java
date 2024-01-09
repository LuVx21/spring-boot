package org.luvx.boot.mq.pulsar.consumer;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.luvx.boot.mq.pulsar.config.User;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.luvx.boot.mq.pulsar.config.ConstValue.*;

@Slf4j
@Service
public class PulsarConsumer {
    @PulsarListener(
            subscriptionName = "string-topic-subscription",
            topics = STRING_TOPIC,
            subscriptionType = SubscriptionType.Shared
    )
    public void stringTopicListener(String str) {
        log.info("收到简单消息: {}", str);
    }


    @PulsarListener(
            subscriptionName = "user-topic-subscription",
            topics = USER_TOPIC,
            subscriptionType = SubscriptionType.Shared,
            schemaType = SchemaType.JSON,
            deadLetterPolicy = "deadLetterPolicy"
    )
    public void userTopicListener(User user) {
        log.info("收到复杂消息: {}", JSON.toJSONString(user));
    }

    // @PulsarListener(topics = USER_TOPIC, subscriptionName = USER_TOPIC + "-sub-batch", batch = true)
    // public void aa(List<User> messages) {
    //     messages.forEach((msg) -> log.info("收到批量复杂消息->", msg));
    // }


    @PulsarListener(
            subscriptionName = "dead-letter-topic-subscription",
            topics = USER_DEAD_LETTER_TOPIC,
            subscriptionType = SubscriptionType.Shared
    )
    public void userDlqTopicListener(User user) {
        log.info("收到私信队列消息: {}", JSON.toJSONString(user));
    }
}