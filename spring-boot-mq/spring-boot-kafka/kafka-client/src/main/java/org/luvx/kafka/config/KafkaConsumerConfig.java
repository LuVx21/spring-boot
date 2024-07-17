package org.luvx.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
}
