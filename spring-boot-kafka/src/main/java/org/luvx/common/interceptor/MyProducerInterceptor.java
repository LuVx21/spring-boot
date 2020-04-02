package org.luvx.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.luvx.common.entity.User;

import java.util.Map;

/**
 * 自定义拦截器
 *
 * @ClassName: org.luvx
 * @Description:
 * @Author: Ren, Xie
 */
@Slf4j
public class MyProducerInterceptor implements ProducerInterceptor<String, User> {
    /**
     * send()后用
     * 消息被序列化以及计算分区前调用
     *
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, User> onSend(ProducerRecord<String, User> record) {
        log.info("拦截前:{}", record.value());
        record.value().setUserName(record.value().getUserName() + "_foo");
        ProducerRecord<String, User> newRecord = new ProducerRecord(
                record.topic(),
                record.partition(),
                record.timestamp(),
                record.key(),
                record.value()
        );
        log.info("拦截后:{}", newRecord.value());
        return newRecord;
    }

    /**
     * 发送到 Kafka Broker 之后 && 回调逻辑触发之前
     *
     * @param data
     * @param ex
     */
    @Override
    public void onAcknowledgement(RecordMetadata data, Exception ex) {
        log.info("拦截到 ack:{}", data.toString());
        if (ex != null) {
            log.info("拦截 ack异常:{}", ex);
        }
    }

    @Override
    public void close() {
        log.info("关闭拦截器");
    }

    @Override
    public void configure(Map<String, ?> map) {
        Object s = map.get(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG);
        log.info("拦截器配置:{}", s);
    }
}
