package org.luvx.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import jakarta.annotation.Resource;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConsumerConfig {

    @Resource
    private KafkaProperties kafkaProperties;

    /**
     * 添加KafkaListenerContainerFactory，用于批量消费消息
     */
    @Bean
    public KafkaListenerContainerFactory<?> batchFactory() {
        Map<String, Object> consumerConfigs = consumerConfigs();
        consumerConfigs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerConfigs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15_000);
        consumerConfigs.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10_000);
        // 稍微大些能看到批量的效果
        consumerConfigs.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 2048);

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new
                ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfigs));
        KafkaProperties.Listener listener = kafkaProperties.getListener();
        if (listener != null) {
            // 开启批量监听
            factory.setBatchListener(true);
            // 并发消费线程
            factory.setConcurrency(listener.getConcurrency());
            // 每一批读取间隔时间
            factory.getContainerProperties().setPollTimeout(listener.getPollTimeout().toMillis());
        }
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        // 阻塞式重试
        factory.setCommonErrorHandler(errorHandler());

        return factory;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        KafkaProperties.Consumer consumer = kafkaProperties.getConsumer();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());

        // 批量消费的数量
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumer.getMaxPollRecords());
        // 每一批读取间隔时间
        // props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, (int) consumer.getFetchMaxWait().toMillis());
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, (int) consumer.getFetchMaxWait().toMillis());
        // 最早未被消费的offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.getAutoOffsetReset());

        // 是否自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumer.getEnableAutoCommit());
        // 自动提交间隔
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, (int) consumer.getAutoCommitInterval().toMillis());

        // props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 180000);
        // props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 180000);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    /**
     * 阻塞式重试: 消息处理失败时，消费者会阻塞，直到重试机制完成重试或达到最大重试次数
     */
    @Bean
    public DefaultErrorHandler errorHandler() {
        long interval = 5000L, maxAttempts = 5L;
        BackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            log.warn("消费重试结束仍失败:{}", consumerRecord, exception);
            // 当所有重试尝试都用尽时执行的逻辑
        }, fixedBackOff);

        // 可重试的异常
        errorHandler.addRetryableExceptions(SocketTimeoutException.class);
        // 不可重试的异常
        errorHandler.addNotRetryableExceptions(NullPointerException.class);

        return errorHandler;
    }

    // @Bean
    // public DefaultErrorHandler kafkaErrorHandler(KafkaTemplate<?, ?> template) {
    //     log.warn("kafkaErrorHandler begin to Handle");
    //
    //     ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
    //     BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
    //     return new DefaultErrorHandler(recoverer, backOff);
    // }
}
