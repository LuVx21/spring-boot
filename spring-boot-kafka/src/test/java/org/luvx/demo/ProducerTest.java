package org.luvx.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.luvx.utils.KafkaUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @ClassName: org.luvx.demo
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/29 16:11
 */
public class ProducerTest {
    final String topicName = "foo";

    @Test
    public void run00() {
        Resource resource = new ClassPathResource("kafka/kafka-producer.properties");
        Properties properties = KafkaUtils.convertToProperties(resource);

        try (Producer<String, String> producer = new KafkaProducer<>(properties);) {
            for (int i = 0; i < 10; i++) {
                producer.send(new ProducerRecord<>(topicName, "key:" + i, "value:" + i));
            }
            System.out.println("Message sent successfully");
        }
    }
}
