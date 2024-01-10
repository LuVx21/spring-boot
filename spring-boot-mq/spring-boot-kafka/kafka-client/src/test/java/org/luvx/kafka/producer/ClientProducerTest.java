package org.luvx.kafka.producer;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.kafka.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

class ClientProducerTest extends ApplicationTests {
    @Autowired
    private ClientProducer    producer;
    @Autowired
    private OldClientProducer oldProducer;

    @Test
    void send() throws InterruptedException {
        while (true) {
            long l = System.currentTimeMillis();
            User user = User.builder()
                    .userId(l)
                    .userName(String.valueOf(LocalDateTime.now()))
                    .age((int) (l % 100))
                    .build();
            producer.send(user);
            // producer.send1(user);
            // oldProducer.send(user);
            // oldProducer.send1(user);

            Thread.sleep(5_000);
        }
    }
}