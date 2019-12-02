package org.luvx.old;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.luvx.utils.KafkaUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName: org.luvx._new
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 11:09
 */
@Slf4j
@Component
public class LogConsumer1 {
    private static final String topic = "foo";

    public void get() {
        Properties props = KafkaUtils.getConsumerProp();
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            KafkaUtils.print(records);
        }
    }

    public void get1() {
        Properties props = KafkaUtils.getConsumerProp();
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        TopicPartition partition = new TopicPartition(topic, 0);
        consumer.assign(Arrays.asList(partition));
        consumer.seek(partition, 0);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            KafkaUtils.print(records);
            consumer.commitSync();
        }
    }

    public static void main(String[] args) {
        new LogConsumer1().get();
    }
}
