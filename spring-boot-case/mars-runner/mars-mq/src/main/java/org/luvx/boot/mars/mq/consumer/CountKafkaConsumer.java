package org.luvx.boot.mars.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luvx.boot.mars.service.kafka.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CountKafkaConsumer {
    @KafkaListener(id = "mars-consumer",
            topics = KafkaTopics.topic_count, groupId = KafkaTopics.group_count
    )
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("计数服务异步变更");
    }
}
