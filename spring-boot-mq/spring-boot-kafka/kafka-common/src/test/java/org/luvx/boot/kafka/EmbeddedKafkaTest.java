package org.luvx.boot.kafka;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.kafka.common.config.KafkaConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.kafka.test.EmbeddedKafkaBroker.BROKER_LIST_PROPERTY;

@ExtendWith(SpringExtension.class)
@EmbeddedKafka(
        count = 4,
        ports = {59092, 59093, 59094, 59095},
        partitions = 3,
        zookeeperPort = 52181,
        topics = {KafkaConfig.TOPIC_SIMPLE}
)
@ImportAutoConfiguration(KafkaAutoConfiguration.class)
public class EmbeddedKafkaTest {
    static {
        System.setProperty(BROKER_LIST_PROPERTY, "spring.kafka.bootstrap-servers");
    }

    @Test
    public void run() throws IOException {
        System.in.read();
    }

    @Test
    void m1() {
        // KafkaTestUtils.();
        // KafkaTestUtils.getSingleRecord()
    }
}
