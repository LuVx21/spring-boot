package org.luvx.boot.mq.rocketmq.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.luvx.boot.mq.rocketmq.config.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class Producer {
    @Autowired
    private RocketMQTemplate rocketmqTemplate;

    public void sendString(String topic, String msg) {
        rocketmqTemplate.convertAndSend(topic, msg + LocalDateTime.now());
        // Message<String> msg = MessageBuilder.withPayload(payload).build();
        // rocketmqTemplate.send(topic, msg);
    }

    public void sendDelay(String topic, String payload) {
        Map<String, Object> headers = Map.of(RocketMQHeaders.KEYS, "uuid:" + UUID.randomUUID());
        Message<String> message = MessageBuilder
                .withPayload("延迟消息:" + payload + "" 时间:" + LocalDateTime.now())
                .copyHeaders(headers)
                .build();
        // 延迟级别 1-18
        // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        SendResult sendResult = rocketmqTemplate.syncSend(topic + ":tag", message, 60_000, 2);
        if (SendStatus.SEND_OK == sendResult.getSendStatus()) {
            log.info("发送延迟消息成功,返回结果:{}", JSON.toJSONString(sendResult));
        } else {
            log.error("发送延迟消息失败,返回结果:{}", JSON.toJSONString(sendResult));
        }
    }

    public void sendUser(String topic, String msg) {
        User user = new User();
        user.setId(10000L)
                .setUserName(msg + ":userName")
                .setPassword(msg + ":password")
                .setBirthday(LocalDateTime.now());

        rocketmqTemplate.convertAndSend(topic, user);
    }
}
