package org.luvx.kafka.partitioner;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Cluster;
import org.luvx.kafka.common.entity.User;

import java.util.Map;

/**
 * 自定义分区器
 *
 * @author renxie
 */
@Slf4j
public class MyPartitioner implements Partitioner {
    /**
     * 关键分区计算
     * 假设以年龄的奇偶进行分区
     *
     * @param topic
     * @param key
     * @param keyBytes
     * @param value
     * @param valueBytes
     * @param cluster
     * @return 分区 id
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (value instanceof User) {
            int age = ((User) value).getAge();
            int id = (age & 1) == 1 ? 0 : 1;
            log.info("消息分到了分区:{}({})", id, value);
            return id;
        }

        return 0;
    }

    @Override
    public void close() {
        log.info("关闭分区器");
    }

    /**
     * 从配置中获取内容
     *
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {
        Object s = configs.get(ProducerConfig.PARTITIONER_CLASS_CONFIG);
        log.info("分区器配置:{}", s);
    }
}
