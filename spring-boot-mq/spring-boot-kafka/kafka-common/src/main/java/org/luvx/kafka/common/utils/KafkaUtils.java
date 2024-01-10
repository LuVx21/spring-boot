package org.luvx.kafka.common.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Streams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
public class KafkaUtils {
    private final static String PREFIX_KAFKA          = "kafka.";
    private final static String PREFIX_KAFKA_CONSUMER = "kafka.consumer.";
    private final static String PREFIX_KAFKA_PRODUCER = "kafka.producer.";
    private final static String PREFIX_TOPIC          = "topic.regex";
    private static final String CONFIG_PATH_PRODUCER  = "config/kafka/kafka-producer.properties";
    private static final String CONFIG_PATH_CONSUMER  = "config/kafka/kafka-consumer.properties";

    public static Properties getProducerProp() {
        return PropertiesUtils.getProperties(CONFIG_PATH_PRODUCER);
    }

    public static Properties getConsumerProp() {
        return PropertiesUtils.getProperties(CONFIG_PATH_CONSUMER);
    }

    /**
     * 测试用: 格式化显示消息
     *
     * @param records
     */
    public static void print(ConsumerRecords<?, ?> records) {
        for (ConsumerRecord<?, ?> record : records) {
            print(record);
        }
    }

    /**
     * 测试用: 格式化显示消息
     *
     * @param record
     */
    public static void print(ConsumerRecord<?, ?> record) {
        String topic = record.topic();
        int partitionNum = record.partition();
        long offset = record.offset();
        Object key = record.key();
        Object value = record.value();
        log.info("------------------消费↓------------------------");
        log.info("Topic: {} Partition: {} offset: {}", topic, partitionNum, offset);
        log.info("key: {} msg: {}", key, value);
        log.info("------------------消费↑------------------------");
    }

    /**
     * 拼接topic
     *
     * @param topicPrefix topic前缀
     */
    public static List<String> resolveTopics(String topicPrefix, Collection<String> topics) {
        Assert.notEmpty(topics, "Topics不可为空");
        return topics.stream()
                .map(topic -> topicPrefix + topic)
                .collect(Collectors.toList());
    }

    public static <K, V> MessageConsumer<K, V> createConsumer(String consumerClass, Properties properties,
                                                              String topicPrefix, List<String> topics) {
        log.info("Create a consumer: consumerClass={},topicPrefix={},topics={},properties={}",
                consumerClass, topicPrefix, JSONObject.toJSONString(topics), properties);
        try {
            String topicRegex = retrieveTopicRegex(properties);
            Class<?> clazz = ClassUtils.forName(consumerClass, ClassUtils.getDefaultClassLoader());
            final MessageConsumer<K, V> consumer = (MessageConsumer<K, V>) clazz
                    .getDeclaredConstructor(new Class[]{String.class, Properties.class})
                    .newInstance(new Object[]{topicPrefix, properties});
            consumer.afterPropertiesSet();
            if (StringUtils.hasText(topicRegex)) {
                consumer.getNativeConsumer().subscribe(Pattern.compile(topicRegex),
                        new DefaultConsumerRebalanceListener<>(consumer));
            } else if (!CollectionUtils.isEmpty(topics)) {
                consumer.subscribe(topics, new DefaultConsumerRebalanceListener<>(consumer));
            }
            return consumer;
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <K, V> Map<TopicPartition, OffsetAndMetadata> convertToOffsets(ConsumerRecords<K, V> records) {
        return Streams.stream(records)
                .collect(Collectors.toMap(
                        record -> new TopicPartition(record.topic(), record.partition()),
                        record -> new OffsetAndMetadata(record.offset() + 1),
                        (a, b) -> b
                ));
    }

    private static String retrieveTopicRegex(Properties properties) {
        String value = properties.getProperty(PREFIX_TOPIC);
        if (StringUtils.hasLength(value)) {
            properties.remove(PREFIX_TOPIC);
        }
        return value;
    }

    public static <K, V> MessageListener<K, V> createMessageListener(String messageListenerClass) {
        Assert.hasText(messageListenerClass, "MessageListener should not be empty!");
        MessageListener<K, V> listener = null;
        try {
            Class<?> clazz = ClassUtils.forName(messageListenerClass, ClassUtils.getDefaultClassLoader());
            listener = (MessageListener<K, V>) clazz.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return listener;
    }

    /**
     * 简化配置
     *
     * @param properties
     * @param otherPrefix
     * @return
     */
    public static Properties retrieveProperties(Properties properties, String otherPrefix) {
        Properties p = new Properties();
        Set<Map.Entry<Object, Object>> set = properties.entrySet();
        String key = null;
        for (Map.Entry<Object, Object> entry : set) {
            key = (String) entry.getKey();
            if (StringUtils.hasText(key)) {

                if (key.startsWith(otherPrefix)) {
                    p.put(key.substring(otherPrefix.length()), entry.getValue());
                } else if (key.startsWith(PREFIX_KAFKA)) {

                    if (PREFIX_KAFKA_PRODUCER.equals(otherPrefix)) {
                        if (!key.startsWith(PREFIX_KAFKA_CONSUMER)) {
                            p.put(key.substring(PREFIX_KAFKA.length()), entry.getValue());
                        }
                    } else if (PREFIX_KAFKA_CONSUMER.equals(otherPrefix)) {
                        if (!key.startsWith(PREFIX_KAFKA_PRODUCER)) {
                            p.put(key.substring(PREFIX_KAFKA.length()), entry.getValue());
                        }
                    }

                }
            }
        }
        return p;
    }

    private static class DefaultConsumerRebalanceListener<K, V> implements ConsumerRebalanceListener {
        private MessageConsumer<K, V> consumer;

        public DefaultConsumerRebalanceListener(MessageConsumer<K, V> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            consumer.commitSync();
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        }
    }

    public interface MessageListener<K, V> {

        void onMessage(ConsumerRecords<K, V> data);

        void onMessage(ConsumerRecords<K, V> data, OffsetCommitCallback callback);
    }

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
}