package org.luvx.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@Component
public class KafkaDynamicConsumerFactory {
    @Resource
    private Map<String, Object> consumerConfigs;

    /**
     * 创建一个Kafka消费者
     *
     * @param topic   消费者订阅的话题
     * @param groupId 消费者组名
     * @return 消费者对象
     */
    public <K, V> KafkaConsumer<K, V> createConsumer(String topic, String groupId) {
        Properties p = new Properties();
        p.putAll(consumerConfigs);

        p.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // p.put("offsets.retention.minutes", 1440);
        // p.put("auto.create.groups.enable", false);
        // 设定信任所有类型以反序列化
        p.put("spring.json.trusted.packages", "*");
        KafkaConsumer<K, V> consumer = new KafkaConsumer<>(p);
        consumer.subscribe(Collections.singleton(topic));
        return consumer;
    }
}
