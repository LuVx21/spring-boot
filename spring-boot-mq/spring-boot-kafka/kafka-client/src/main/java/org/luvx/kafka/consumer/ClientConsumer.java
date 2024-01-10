package org.luvx.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luvx.kafka.common.utils.KafkaUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientConsumer {
    /**
     * topics 来源自
     * {@link org.luvx.kafka.common.config.KafkaTopicConfig#kafkaTopicName}
     *
     * @param record
     */
    @KafkaListener(topics = "#{kafkaTopicName}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<?, ?> record) {
        KafkaUtils.print(record);
    }
}
