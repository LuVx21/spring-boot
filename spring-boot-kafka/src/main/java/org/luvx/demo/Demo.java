package org.luvx.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @ClassName: org.luvx.demo
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/30 9:59
 */
@Slf4j
@Component
public class Demo {

    private static final String topic = "foo";

    @Component
    public static class UserLogProducer {
        @Autowired
        private KafkaTemplate kafkaTemplate;

        public void send(String message) {
            log.info("生产消息:{}", message);
            kafkaTemplate.send(topic, message);
        }
    }

    @Component
    public static class UserLogConsumer {
        @KafkaListener(topics = {topic})
        public void listen(ConsumerRecord<?, ?> consumerRecord) {
            Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
            log.info(">>>>>>>>>> record = {}", kafkaMessage);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                log.info("消费消息:{}", message);
            }
        }
    }
}
