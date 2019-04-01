package org.luvx.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 * @ClassName: org.luvx.kafka
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/5 11:18
 */
public interface MessageListener<K, V> {

    void onMessage(ConsumerRecords<K, V> data);

    // void onMessage(ConsumerRecords<K, V> data, CommitCallback<K, V> callback);
}