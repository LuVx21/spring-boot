package org.luvx.boot.mq.rocketmq.producer;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic, String msg) {
        rocketMQTemplate.convertAndSend(topic, msg);
    }
}
