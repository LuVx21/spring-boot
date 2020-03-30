package org.luvx.old;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.luvx.utils.KafkaUtils;
import org.springframework.stereotype.Component;

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
            // 自动commit已关闭
            // 同步commit
            // consumer.commitSync();
            // 异步commit
            consumer.commitAsync(
                    new OffsetCommitCallback() {
                        @Override
                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                            if (exception != null) {
                                log.error("commit exception! offset:{}", offsets);
                            }
                        }
                    }
            );
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
