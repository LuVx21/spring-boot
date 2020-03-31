package org.luvx.common.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @ClassName: org.luvx
 * @Description:
 * @Author: Ren, Xie
 */
public class MyProducerInterceptor implements ProducerInterceptor<String, String> {
    /**
     * send()后用
     * 消息被序列化以及计算分区前调用
     *
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return new ProducerRecord(record.topic(),
                record.partition(),
                record.timestamp(),
                record.key(),
                System.currentTimeMillis() + ", " + record.value());
    }

    /**
     * 发送到 Kafka Broker 之后 && 回调逻辑触发之前
     *
     * @param recordMetadata
     * @param e
     */
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
