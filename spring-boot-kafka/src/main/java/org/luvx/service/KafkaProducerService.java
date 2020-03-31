package org.luvx.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.luvx.common.config.KafkaConfig;
import org.luvx.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class KafkaProducerService {
    private KafkaTemplate kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 同步生产
     *
     * @param user
     */
    public void send(User user) {
        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(
                KafkaConfig.topic,
                null,
                System.currentTimeMillis(),
                String.valueOf(user.hashCode()),
                user
        );

        SendResult<Integer, User> sendResult = null;
        try {
            sendResult = (SendResult<Integer, User>) kafkaTemplate.send(producerRecord).get();
        } catch (InterruptedException | ExecutionException ex) {
            log.error("发送消息异常 ex:{} topic: {}, msg: {}", ex, KafkaConfig.topic, user);
        }
        if (sendResult.getRecordMetadata() != null) {
            log.info("发送消息成功 topic: {}, msg: {}",
                    sendResult.getProducerRecord().topic(), sendResult.getProducerRecord().value());
        }
    }

    /**
     * 异步生产
     *
     * @param user
     */
    public void send1(User user) {
        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(
                KafkaConfig.topic,
                null,
                System.currentTimeMillis(),
                String.valueOf(user.hashCode()),
                user
        );
        ListenableFuture<SendResult<Integer, User>> future = kafkaTemplate.send(producerRecord);
        /// ListenableFuture<SendResult<Integer, User>> future = kafkaTemplate.send(KafkaTopicConfig.topic, user);
        future.addCallback(
                result -> log.info("发送消息成功 topic: {}, msg: {}",
                        result.getProducerRecord().topic(), result.getProducerRecord().value()),
                ex -> log.error("发送消息异常 ex:{} topic: {}, msg: {}", ex, KafkaConfig.topic, user)
        );
    }
}
