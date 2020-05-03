package org.luvx.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.luvx.common.config.KafkaConfig;
import org.luvx.common.entity.User;

import java.util.Properties;

public class Main {
    /**
     * 场景: 计算各个年龄的用户数
     *
     * @param args
     */
    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, User> source = builder.stream(KafkaConfig.topic);
        KStream<Integer, Long> stream =
                source.selectKey(
                        new KeyValueMapper<String, User, Integer>() {
                            @Override
                            public Integer apply(String key, User value) {
                                return value.getAge();
                            }
                        }
                )
                        .groupByKey()
                        .count()
                        .toStream();

        // stream.print();

        final KafkaStreams streams = new KafkaStreams(builder.build(), getProperties());

        streams.start();
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-temperature");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        return props;
    }
}
