package org.luvx.kafka.producer;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.*;
import org.luvx.kafka.common.config.KafkaConfig;
import org.luvx.kafka.common.entity.User;
import org.luvx.kafka.interceptor.MyProducerInterceptor;
import org.luvx.kafka.partitioner.MyPartitioner;
import org.luvx.kafka.common.utils.KafkaUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class OldClientProducer {
    /**
     * 同步发送消息
     */
    public void send(User user) {
        Properties props = KafkaUtils.getProducerProp();
        addProperties(props);
        Producer<String, User> producer = new KafkaProducer<>(props);

        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(
                KafkaConfig.TOPIC_SIMPLE,
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
            log.error("发送消息异常 ex:{} topic: {}, msg: {}", ex, KafkaConfig.TOPIC_SIMPLE, user);
        }
        producer.flush();
    }

    private void addProperties(Properties props) {
        // 发送消息的本地缓冲区大小, 默认 32M,
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // 批量发送消息的大小, 默认 16k
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 消息在缓冲区最长停留时间
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        /// 自定义分区器
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        /// 添加拦截器
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, MyProducerInterceptor.class.getName());
    }

    /**
     * 异步发送消息
     */
    public void send1(User user) {
        Properties props = KafkaUtils.getProducerProp();
        Producer<String, User> producer = new KafkaProducer<>(props);

        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(
                KafkaConfig.TOPIC_SIMPLE,
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
                        log.error("发送消息异常 ex:{} topic: {}, msg: {}", ex, KafkaConfig.TOPIC_SIMPLE, user);
                    }
                }
        );
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        User user = User.builder()
                .userId(l)
                .userName(String.valueOf(LocalDateTime.now()))
                .age((int) (l % 100))
                .build();
        OldClientProducer producer = new OldClientProducer();
        producer.send(user);
    }
}
