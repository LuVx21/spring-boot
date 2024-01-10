package org.luvx.kafka.streams.transformer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.luvx.kafka.streams.examples.WordCountDemo;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Ren, Xie
 */
public final class WordCountTransformerDemo {
    public static final String SINK_TOPIC = "streams-wordcount-processor-output";

    public static void main(final String[] args) {
        final StreamsBuilder builder = new StreamsBuilder();
        builder.<String, String>stream(WordCountDemo.SOURCE_TOPIC)
                .transform(new WordCountTransformerSupplier())
                .to(SINK_TOPIC);
        final KafkaStreams streams = new KafkaStreams(builder.build(), sourceConfig());

        //<editor-fold desc="通用部分">
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (final Throwable e) {
            System.exit(1);
        }
        System.exit(0);
        //</editor-fold>
    }

    public static Properties sourceConfig() {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount-transformer");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "luvx:59092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

}