package org.luvx.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.Properties;

@Component
public class KafkaDynamicConsumerFactory {

    @Resource
    private KafkaProperties kafkaProperties;

    /**
     * 创建一个Kafka消费者
     *
     * @param topic   消费者订阅的话题
     * @param groupId 消费者组名
     * @return 消费者对象
     */
    public <K, V> KafkaConsumer<K, V> createConsumer(String topic, String groupId) throws ClassNotFoundException {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        KafkaProperties.Consumer consumerConfig = kafkaProperties.getConsumer();
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerConfig.getKeyDeserializer());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerConfig.getValueDeserializer());
        // 设定信任所有类型以反序列化
        consumerProperties.put("spring.json.trusted.packages", "*");
        KafkaConsumer<K, V> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Collections.singleton(topic));
        return consumer;
    }
}
