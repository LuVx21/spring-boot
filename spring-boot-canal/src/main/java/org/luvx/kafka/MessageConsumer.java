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
    /**
     * 获取消费者
     *
     * @return
     */
    KafkaConsumer<K, V> getNativeConsumer();

    void subscribe(Collection<String> topics);

    void subscribe(Collection<String> topics, ConsumerRebalanceListener listener);

    /**
     * 拉取数据
     *
     * @return
     */
    ConsumerRecords<K, V> poll();


    /**
     * 拉取数据
     *
     * @param timeout
     * @return
     */
    ConsumerRecords<K, V> poll(long timeout);

    void seek(TopicPartition topicPartition, long offset);

    void commitSync();

    void commitSync(final Map<TopicPartition, OffsetAndMetadata> offsets);

    void close();
}