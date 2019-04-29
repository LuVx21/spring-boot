package org.luvx.canal.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.FlatMessage;
import com.google.common.collect.ImmutableList;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: org.luvx.canal.mq
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/16 11:10
 */
public class CanalConsumer {

    private static final List<String> topics     = ImmutableList.of("boot");
    private static final List<String> partitions = ImmutableList.of("boot");

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


        Consumer<String, String> consumer = new KafkaConsumer<>(props);


        TopicPartition partition = new TopicPartition("boot", 0);
        // 已存在的group.id
        consumer.assign(Arrays.asList(partition));
        consumer.seek(partition, 0);

        // 如果是之前不存在的group.id
        // Map<TopicPartition, OffsetAndMetadata> hashMaps = new HashMap<>(1);
        // hashMaps.put(p, new OffsetAndMetadata(0));


        /// consumer.subscribe(topics);

        ConsumerRecords<String, String> consumerRecords = null;

        for (int i = 0; i < 100; i++) {
            Thread.sleep(2 * 1000);
            consumerRecords = consumer.poll(1000);
            method(consumerRecords);
        }

        consumer.commitSync();
    }

    private static void method(ConsumerRecords<String, String> consumerRecords) {
        for (ConsumerRecord<String, String> record : consumerRecords) {

            int partition = record.partition();
            long offset = record.offset();
            Object key = record.key();
            String value = record.value();

            FlatMessage message = JSON.parseObject(value, FlatMessage.class);

            System.out.println("==========================================");
            System.out.println(partition + " " + offset + " " + key + " " + value);
            System.out.println(message);
            System.out.println("==========================================");
        }
    }
}
