package org.luvx.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.luvx.kafka.common.config.KafkaConfig;
import org.luvx.kafka.common.entity.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ClientProducer {
    @Resource
    private KafkaTemplate<String, User> kafkaTemplate;
    @Resource
    private String[]                    kafkaTopicName;

    /**
     * 同步生产
     */
    public void send(User user) {
        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(
                // KafkaConfig.TOPIC_SIMPLE,
                kafkaTopicName[0],
                null,
                System.currentTimeMillis(),
                String.valueOf(user.hashCode()),
                user
        );

        SendResult<String, User> sendResult = null;
        try {
            sendResult = kafkaTemplate.send(producerRecord).get();
        } catch (InterruptedException | ExecutionException ex) {
            log.error("发送消息异常 ex:{} topic: {}, msg: {}", ex, KafkaConfig.TOPIC_SIMPLE, user);
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
                KafkaConfig.TOPIC_SIMPLE,
                null,
                System.currentTimeMillis(),
                String.valueOf(user.hashCode()),
                user
        );
        CompletableFuture<SendResult<String, User>> future = kafkaTemplate.send(producerRecord);
        future.thenAcceptAsync(result -> log.info("发送消息成功 topic: {}, msg: {}", result.getProducerRecord().topic(), result.getProducerRecord().value()))
                .exceptionally(ex -> {
                    log.error("发送消息异常 ex:{} topic: {}, msg: {}", ex, KafkaConfig.TOPIC_SIMPLE, user);
                    return null;
                });
    }
}
