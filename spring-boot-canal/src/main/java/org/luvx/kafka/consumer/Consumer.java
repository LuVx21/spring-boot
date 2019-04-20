package org.luvx.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName: org.luvx.kafka.consumer
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/16 11:47
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", "logGroup");
        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "earliest");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        org.apache.kafka.clients.consumer.Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("example"));

        ConsumerRecords<String, String> consumerRecords = null;

        for (int i = 0; i < 100; i++) {
            Thread.sleep(2 * 1000);
            consumerRecords = consumer.poll(1000);

            for (ConsumerRecord<String, String> record : consumerRecords) {
                long offset = record.offset();
                int partition = record.partition();
                Object key = record.key();
                Object value = record.value();
                System.out.println("------------------------------------------");
                System.out.println(offset + " " + partition + " " + key + " " + value);
                System.out.println("------------------------------------------");
            }
            consumer.commitSync();
        }
    }
}
