package org.luvx.boot.mq.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.luvx.boot.mq.rocketmq.config.ConstValue;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = ConstValue.STRING_TOPIC, consumerGroup = "MyConsumerGroup")
public class Consumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("收到简单消息: {}", message);
    }
}
