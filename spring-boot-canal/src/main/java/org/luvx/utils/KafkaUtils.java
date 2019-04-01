package org.luvx.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.luvx.kafka.MessageConsumer;
import org.luvx.kafka.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @ClassName: org.luvx.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/5 11:10
 */
public class KafkaUtils {
    private final static Logger logger                   = LoggerFactory.getLogger(KafkaUtils.class);
    private final static String PROPERTY_PREFIX          = "kafka.";
    private final static String PROPERTY_CONSUMER_PREFIX = "kafka.consumer.";
    private final static String PROPERTY_PRODUCER_PREFIX = "kafka.producer.";
    public final static  String TOPIC_PATTERN_KEY        = "topic.regex";

    public static Collection<String> resolveTopics(Collection<String> topics, String topicPrefix) {
        Assert.notEmpty(topics, "Topics should not be empty!");
        List<String> list = new ArrayList<String>(topics.size());
        for (String topic : topics) {
            list.add(topicPrefix + topic);
        }
        return list;
    }

    public static <K, V> Map<TopicPartition, OffsetAndMetadata> convertToOffsets(
            ConsumerRecords<K, V> records) {
        Iterator<ConsumerRecord<K, V>> iterator = records.iterator();
        Map<TopicPartition, OffsetAndMetadata> offsets =
                new LinkedHashMap<TopicPartition, OffsetAndMetadata>();
        ConsumerRecord<K, V> record = null;
        while (iterator.hasNext()) {
            record = iterator.next();
            offsets.put(new TopicPartition(record.topic(), record.partition()),
                    new OffsetAndMetadata(record.offset() + 1));
        }
        return offsets;
    }

    public static <K, V> Map<TopicPartition, OffsetAndMetadata> convertToOffsets(
            ConsumerRecord<K, V> record) {
        Map<TopicPartition, OffsetAndMetadata> offsets =
                new LinkedHashMap<TopicPartition, OffsetAndMetadata>();
        offsets.put(new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1));
        return offsets;
    }

    /**
     * Create a new consumer.
     *
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
    public static <K, V> MessageConsumer<K, V> createConsumer(String consumerClass,
                                                              Properties properties, String topicPrefix, List<String> topics) {
        logger.info("Create a consumer: consumerClass={},topicPrefix={},topics={},properties={}",
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
                        new DefaultConsumerRebalanceListener<K, V>(consumer));
            } else if (!CollectionUtils.isEmpty(topics)) {
                consumer.subscribe(topics, new DefaultConsumerRebalanceListener<K, V>(consumer));
            }
            return consumer;
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
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
        return retrieveProperties(convertToProperties(resource), PROPERTY_CONSUMER_PREFIX);
    }

    public static Properties retrieveConsumerProperties(Properties properties) {
        return retrieveProperties(properties, PROPERTY_CONSUMER_PREFIX);
    }

    public static Properties retrieveProducerProperties(Resource resource) {
        return retrieveProperties(convertToProperties(resource), PROPERTY_PRODUCER_PREFIX);
    }

    public static Properties retrieveProducerProperties(Properties properties) {
        return retrieveProperties(properties, PROPERTY_PRODUCER_PREFIX);
    }

    private static Properties retrieveProperties(Properties properties, String otherPrefix) {
        Properties kafkaPro = new Properties();
        Set<java.util.Map.Entry<Object, Object>> set = properties.entrySet();
        String key = null;
        for (java.util.Map.Entry<Object, Object> entry : set) {
            key = (String) entry.getKey();
            if (StringUtils.hasText(key)) {
                if (key.startsWith(otherPrefix)) {
                    kafkaPro.put(key.substring(otherPrefix.length()), entry.getValue());
                } else if (key.startsWith(PROPERTY_PREFIX, 0)) {
                    if ((PROPERTY_PRODUCER_PREFIX.equals(otherPrefix)
                            && !key.startsWith(PROPERTY_CONSUMER_PREFIX))
                            || (PROPERTY_CONSUMER_PREFIX.equals(otherPrefix)
                            && !key.startsWith(PROPERTY_PRODUCER_PREFIX))) {
                        kafkaPro.put(key.substring(PROPERTY_PREFIX.length()), entry.getValue());
                    }
                }
            }
        }
        return kafkaPro;
    }

    public static Properties convertToProperties(Resource resource) {
        try {
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
        }
        return null;
    }

    private static class DefaultConsumerRebalanceListener<K, V> implements ConsumerRebalanceListener {
        private MessageConsumer<K, V> consumer;

        public DefaultConsumerRebalanceListener(MessageConsumer<K, V> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            consumer.commitSync();
            logger.info("Rebalance occured, commit offset manually!");
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            logger.info("Partitions are assigned");
        }
    }
}