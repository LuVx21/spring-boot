package org.luvx.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.luvx.common.config.KafkaConfig;
import org.luvx.common.entity.User;
import org.luvx.common.partitioner.MyPartitioner;
import org.luvx.common.utils.KafkaUtils;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @ClassName: org.luvx._new
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 11:09
 */
@Slf4j
@Service
public class OldKafkaProducerService {
    /**
     * 同步发送消息
     */
    public void send(User user) {
        Properties props = KafkaUtils.getProducerProp();
        /// 自定义分区器
        /// props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        Producer<String, User> producer = new KafkaProducer<>(props);

        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(
                KafkaConfig.topic,
                null,
                System.currentTimeMillis(),
                String.valueOf(user.hashCode()),
                user
        );

        Future<RecordMetadata> future = producer.send(producerRecord);
        try {
            RecordMetadata data = future.get();
            log.info("发送消息成功 topic: {}, msg: {}", data.topic(), user);
        } catch (ExecutionException | InterruptedException ex) {
            log.error("发送消息异常 ex:{} topic: {}, msg: {}", ex, KafkaConfig.topic, user);
        }
        producer.flush();
    }

    /**
     * 异步发送消息
     */
    public void send1(User user) {
        Properties props = KafkaUtils.getProducerProp();
        Producer<String, User> producer = new KafkaProducer<>(props);

        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(
                KafkaConfig.topic,
                null,
                System.currentTimeMillis(),
                String.valueOf(user.hashCode()),
                user
        );

        producer.send(
                producerRecord,
                (data, ex) -> {
                    //如果Kafka返回一个错误，onCompletion方法抛出一个non null异常。
                    if (ex == null) {
                        log.info("发送消息成功 topic: {}, msg: {}", data.topic(), user);
                    } else {
                        log.error("发送消息异常 ex:{} topic: {}, msg: {}", ex, KafkaConfig.topic, user);
                    }
                }
        );
    }
}
