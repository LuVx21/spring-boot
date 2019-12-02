package org.luvx.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @ClassName: org.luvx.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/5 11:10
 */
@Slf4j
public class KafkaUtils {
    private final static String PROPERTY_PREFIX          = "kafka.";
    private final static String PROPERTY_CONSUMER_PREFIX = "kafka.consumer.";
    private final static String PROPERTY_PRODUCER_PREFIX = "kafka.producer.";
    private final static String TOPIC_PATTERN_KEY        = "topic.regex";

    public static Properties getProducerProp() {
        return PropertiesUtils.getProperties("config/kafka/kafka-producer.properties");
    }

    public static Properties getConsumerProp() {
        return PropertiesUtils.getProperties("config/kafka/kafka-consumer.properties");
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
        log.info("Topic:{} Partition:{} offset:{} key:{} msg:{}", topic, partitionNum, offset, key, value);
        log.info("------------------消费↑------------------------");
    }

    /**
     * 拼接topic
     *
     * @param topics
     * @param topicPrefix topic前缀
     * @return
     */
    public static Collection<String> resolveTopics(Collection<String> topics, String topicPrefix) {
        Assert.notEmpty(topics, "Topics should not be empty!");
        List<String> list = new ArrayList<>(topics.size());
        for (String topic : topics) {
            list.add(topicPrefix + topic);
        }
        return list;
    }

    /**
     * 创建消费者
     *
     * @param consumerClass
     * @param resource
     * @param topicPrefix
     * @param topics
     * @return
     */
    public static <K, V> MessageConsumer<K, V> createConsumer(String consumerClass, Resource resource,
                                                              String topicPrefix, List<String> topics) {
        return createConsumer(consumerClass, retrieveConsumerProperties(resource), topicPrefix, topics);
    }

    /**
     * Create a new consumer.
     *
     * @return
     */
    public static <K, V> MessageConsumer<K, V> createConsumer(String consumerClass,
                                                              Properties properties, String topicPrefix) {
        return createConsumer(consumerClass, retrieveConsumerProperties(properties), topicPrefix, null);
    }

    /**
     * Create a new consumer.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
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
        Iterator<ConsumerRecord<K, V>> iterator = records.iterator();
        Map<TopicPartition, OffsetAndMetadata> offsets = new LinkedHashMap<>();
        ConsumerRecord<K, V> record = null;
        while (iterator.hasNext()) {
            record = iterator.next();
            offsets.put(new TopicPartition(record.topic(), record.partition()),
                    new OffsetAndMetadata(record.offset() + 1));
        }
        return offsets;
    }

    public static <K, V> Map<TopicPartition, OffsetAndMetadata> convertToOffsets(ConsumerRecord<K, V> record) {
        Map<TopicPartition, OffsetAndMetadata> offsets = new LinkedHashMap<>();
        offsets.put(new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1));
        return offsets;
    }

    private static String retrieveTopicRegex(Properties properties) {
        String patternValue = properties.getProperty(TOPIC_PATTERN_KEY);
        if (StringUtils.hasLength(patternValue)) {
            properties.remove(TOPIC_PATTERN_KEY);
        }
        return patternValue;
    }

    @SuppressWarnings("unchecked")
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

    public static Properties retrieveConsumerProperties(Resource resource) {
        return retrieveProperties(PropertiesUtils.getProperties(resource), PROPERTY_CONSUMER_PREFIX);
    }

    /**
     * @param properties
     * @return
     */
    public static Properties retrieveConsumerProperties(Properties properties) {
        return retrieveProperties(properties, PROPERTY_CONSUMER_PREFIX);
    }

    public static Properties retrieveProducerProperties(Resource resource) {
        return retrieveProperties(PropertiesUtils.getProperties(resource), PROPERTY_PRODUCER_PREFIX);
    }

    public static Properties retrieveProducerProperties(Properties properties) {
        return retrieveProperties(properties, PROPERTY_PRODUCER_PREFIX);
    }

    private static Properties retrieveProperties(Properties properties, String otherPrefix) {
        Properties p = new Properties();
        Set<java.util.Map.Entry<Object, Object>> set = properties.entrySet();
        String key = null;
        for (java.util.Map.Entry<Object, Object> entry : set) {
            key = (String) entry.getKey();
            if (StringUtils.hasText(key)) {
                if (key.startsWith(otherPrefix)) {
                    p.put(key.substring(otherPrefix.length()), entry.getValue());
                } else if (key.startsWith(PROPERTY_PREFIX, 0)) {
                    if ((PROPERTY_PRODUCER_PREFIX.equals(otherPrefix)
                            && !key.startsWith(PROPERTY_CONSUMER_PREFIX))
                            || (PROPERTY_CONSUMER_PREFIX.equals(otherPrefix)
                            && !key.startsWith(PROPERTY_PRODUCER_PREFIX))) {
                        p.put(key.substring(PROPERTY_PREFIX.length()), entry.getValue());
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
            log.info("Rebalance occured, commit offset manually!");
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            log.info("Partitions are assigned");
        }
    }

    public interface MessageListener<K, V> {

        void onMessage(ConsumerRecords<K, V> data);

        // void onMessage(ConsumerRecords<K, V> data, CommitCallback<K, V> callback);
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