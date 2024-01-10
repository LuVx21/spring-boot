package org.luvx.kafka.streams.examples;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Ren, Xie
 * <pre>
 *     mvn exec:java -Dexec.mainClass=org.luvx.kafka.streams.examples.PipeStream
 * </pre>
 */
public class PipeStream {
    public static final String SOURCE_TOPIC = "streams-plaintext-input";
    public static final String SINK_TOPIC   = "streams-pipe-output";

    public static void main(String[] args) {
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream(SOURCE_TOPIC).to(SINK_TOPIC);

        final KafkaStreams streams = new KafkaStreams(builder.build(), sourceConfig());

        //<editor-fold desc="通用部分">
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("streams-pipe-shutdown-hook") {
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
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "luvx:59092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return props;
    }
}
