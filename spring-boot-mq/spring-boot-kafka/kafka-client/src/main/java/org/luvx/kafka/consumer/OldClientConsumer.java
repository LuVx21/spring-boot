package org.luvx.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.luvx.kafka.common.config.KafkaConfig;
import org.luvx.kafka.common.utils.KafkaUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: org.luvx._new
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 11:09
 */
@Slf4j
@Service
public class OldClientConsumer {
    /**
     * 消费: 同步提交
     * 已关闭自动提交
     */
    public void get() {
        Properties props = KafkaUtils.getConsumerProp();
        addProperties(props);
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        List<TopicPartition> partitions = new ArrayList<>();
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(KafkaConfig.TOPIC_SIMPLE);
        for (PartitionInfo info : partitionInfos) {
            if (info.partition() <= 1) {
                TopicPartition partition = new TopicPartition(info.topic(), info.partition());
                /// consumer.seek(partition, 0);
                partitions.add(partition);
            }
        }
        consumer.assign(partitions);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            KafkaUtils.print(records);
            consumer.commitSync();
        }
    }

    private void addProperties(Properties props) {
        // consumer给 broker发送心跳的时间间隔
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);
        // broker 感知不到 consumer 的最大时间, 超过了会认为 consumer 挂了
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10 * 1000);
        // consumer 每次请求的最大记录数, 消费者处理速度快则可以适当调大
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        // 两次拉取的最大时间间隔, 超过了会认为消费者处理能力弱, 会将其踢出消费区, 触发再平衡
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);
    }

    /**
     * 消费: 异步提交
     * 已关闭自动提交
     * <p>
     * 配合同步提交保证最大限度的成功提交
     */
    public void get1() {
        Properties props = KafkaUtils.getConsumerProp();
        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            // 1. 不监听再平衡
            // consumer.subscribe(Arrays.asList(KafkaConfig.topic));
            // 2. 监听再平衡
            consumer.subscribe(List.of(KafkaConfig.TOPIC_SIMPLE), new ConsumerRebalanceListener() {
                /**
                 * 停止消费消息后, 再均衡前调用
                 */
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    log.info("再均衡开始:{}", LocalDateTime.now());
                    consumer.commitSync();
                }

                /**
                 * 再均衡, 开始消费消息前调用
                 */
                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                    log.info("再均衡结束:{}", LocalDateTime.now());
                }
            });

            // 1. 不指定offset,默认提交最大 offset
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    KafkaUtils.print(records);
                    consumer.commitAsync((offsets, exception) -> {
                        if (exception != null) {
                            log.error("commit exception! offset:{}", offsets);
                        }
                    });
                }
            } finally {
                consumer.commitSync();
            }

            /*
            // 2. 指定提交的偏移量
            Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>(8);
            while (true) {
                ConsumerRecords<String, String> records1 = consumer.poll(Duration.ofMillis(100));
                // KafkaUtils.print(records);
                for (ConsumerRecord<String, String> record : records1) {
                    TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                    OffsetAndMetadata om = new OffsetAndMetadata(record.offset() + 1, "nothing");
                    offsets.put(tp, om);
                }
                consumer.commitAsync(offsets, null);
            }
            */
        } catch (Exception _) {
        }
    }

    public static void main(String[] args) {
        new OldClientConsumer().get();
    }
}
