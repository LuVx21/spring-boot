package org.luvx.demo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.luvx.utils.KafkaUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName: org.luvx.demo
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/29 16:11
 */
public class SimpleTest {
    final String topicName = "foo";

    @Test
    public void run01() {
        Resource resource = new ClassPathResource("config/kafka/kafka-producer.properties");
        Properties properties = KafkaUtils.convertToProperties(resource);

        try (Producer<String, String> producer = new KafkaProducer<>(properties);) {
            for (int i = 0; i < 10; i++) {
                producer.send(new ProducerRecord<>(topicName, "key:" + i, "value:" + i));
            }
            System.out.println("Message sent successfully");
        }
    }

    @Test
    public void run02() throws Exception {
        Resource resource = new ClassPathResource("config/kafka/kafka-consumer.properties");
        Properties properties = KafkaUtils.convertToProperties(resource);

        try (Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            // 1
            // consumer.subscribe(Arrays.asList(topicName));
            // 2
            TopicPartition partition = new TopicPartition(topicName, 0);
            consumer.assign(Arrays.asList(partition));
            consumer.seek(partition, 0);
            ConsumerRecords<String, String> consumerRecords = null;

            for (int i = 0; i < 100; i++) {
                Thread.sleep(2 * 1000);
                consumerRecords = consumer.poll(1000);
                method(consumerRecords);
                consumer.commitSync();
            }
        }
    }

    private static void method(ConsumerRecords<String, String> consumerRecords) {
        for (ConsumerRecord<String, String> record : consumerRecords) {
            int partitionNum = record.partition();
            long offset = record.offset();
            Object key = record.key();
            Object value = record.value();
            System.out.println("------------------------------------------");
            System.out.println(partitionNum + " " + offset + " " + key + " " + value);
            System.out.println("------------------------------------------");
        }
    }
}
