package org.luvx.kafka;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: org.luvx.kafka
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/5 11:17
 */
public interface MessageConsumer<K, V> extends InitializingBean {

    KafkaConsumer<K, V> getNativeConsumer();

    ConsumerRecords<K, V> poll();

    ConsumerRecords<K, V> poll(long timeout);

    void seek(TopicPartition topicPartition, long offset);

    void subscribe(Collection<String> topics);

    void subscribe(Collection<String> topics, ConsumerRebalanceListener listener);

    void commitSync();

    void commitSync(final Map<TopicPartition, OffsetAndMetadata> offsets);

    void close();
}