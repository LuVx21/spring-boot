package org.luvx._new;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luvx.utils.KafkaUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx._new
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 11:09
 */
@Slf4j
@Component
public class LogConsumer {
    private static final String topic = "foo";

    @KafkaListener(topics = "#{kafkaTopicName}")
    public void listen(ConsumerRecord<?, ?> record) {
        KafkaUtils.print(record);
    }
}
