package org.luvx.kafka.config;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.luvx.kafka.common.utils.KafkaUtils;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Kafka消费者任务上下文
 */
@Slf4j
public class KafkaConsumerContext {

    /**
     * 存放所有自己创建的Kafka消费者任务, 实际上只有key有用, 可以是个set结构
     * key: groupId
     * value: kafka消费者任务
     */
    // private static final Map<String, KafkaConsumer<?, ?>> consumerMap = new ConcurrentHashMap<>();
    private static final Set<String> consumerMap = Sets.newConcurrentHashSet();

    /**
     * 存放所有定时任务的哈希表
     * key: groupId
     * value: 定时任务对象，用于定时执行kafka消费者的消息消费任务
     */
    private static final Map<String, ScheduledFuture<?>> scheduleMap = new ConcurrentHashMap<>();

    /**
     * 任务调度器，用于定时任务
     */
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(24);

    /**
     * 添加一个Kafka消费者任务
     * 同一个groupId可以执行多次分配多个消费者, 用于提升消费能力(小于分区数的前提下)
     *
     * @param groupId  消费者的组名
     * @param consumer 消费者对象
     * @param <K>      消息键类型
     * @param <V>      消息值类型
     */
    public static <K, V> void addConsumerTask(String groupId, KafkaConsumer<K, V> consumer) {
        // consumerMap.put(groupId, consumer);
        consumerMap.add(groupId);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            // 每次执行拉取消息之前，先检查订阅者是否已被取消（如果订阅者不存在于订阅者列表中说明被取消了）
            // 因为Kafka消费者对象是非线程安全的，因此在这里把取消订阅的逻辑和拉取并处理消息的逻辑写在一起并放入定时器中
            if (!consumerMap.contains(groupId)) {
                log.info("取消订阅并关闭消费者，停止拉取消息");
                consumer.unsubscribe();
                consumer.close();
                scheduleMap.remove(groupId).cancel(true);
                return;
            }
            ConsumerRecords<K, V> records = consumer.poll(Duration.ofMillis(3000));
            for (ConsumerRecord<K, V> record : records) {
                KafkaUtils.print(record);
                consumer.commitAsync();
            }
        }, 0, 1, TimeUnit.SECONDS);
        scheduleMap.put(groupId, future);
    }

    /**
     * 移除Kafka消费者定时任务并关闭消费者订阅
     *
     * @param groupId 消费者的组名
     */
    public static void removeConsumerTask(String groupId) {
        consumerMap.remove(groupId);
    }
}
