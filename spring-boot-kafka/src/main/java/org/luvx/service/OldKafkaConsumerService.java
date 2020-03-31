package org.luvx.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.luvx.common.config.KafkaConfig;
import org.luvx.common.utils.KafkaUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: org.luvx._new
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 11:09
 */
@Slf4j
@Service
public class OldKafkaConsumerService {
    /**
     * 消费: 同步提交
     * 已关闭自动提交
     */
    public void get() {
        Properties props = KafkaUtils.getConsumerProp();
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        TopicPartition partition = new TopicPartition(KafkaConfig.topic, 0);
        TopicPartition partition1 = new TopicPartition(KafkaConfig.topic, 1);
        consumer.assign(Arrays.asList(partition, partition1));
        /// consumer.seek(partition, 0);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            KafkaUtils.print(records);
            consumer.commitSync();
        }
    }

    /**
     * 消费: 异步提交
     * 已关闭自动提交
     */
    public void get1() {
        Properties props = KafkaUtils.getConsumerProp();
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(KafkaConfig.topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            KafkaUtils.print(records);
            consumer.commitAsync(
                    (offsets, exception) -> {
                        if (exception != null) {
                            log.error("commit exception! offset:{}", offsets);
                        }
                    }
            );
        }
    }

    public static void main(String[] args) {
        new OldKafkaConsumerService().get();
    }
}
