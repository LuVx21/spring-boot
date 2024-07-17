package org.luvx.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luvx.kafka.common.utils.KafkaUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientConsumer {
    /**
     * topics 来源自
     * {@link org.luvx.kafka.common.config.KafkaTopicConfig#kafkaTopicName}
     */
    @KafkaListener(id = "non-batch-consumer", topics = "#{kafkaTopicName}",
            groupId = "non-batch-consumer-group"
            // groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(ConsumerRecord<?, ?> record) {
        KafkaUtils.print(record);
    }

    @KafkaListener(id = "batch-consumer", topics = "#{kafkaTopicName}",
             groupId = "batch-consumer-group",
            containerFactory = "batchFactory")
    public void batchListen(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
        log.info("批量消费大小: {}.", records.size());
        try {
            records.forEach(KafkaUtils::print);
        } finally {
            ack.acknowledge();
        }
    }
}
