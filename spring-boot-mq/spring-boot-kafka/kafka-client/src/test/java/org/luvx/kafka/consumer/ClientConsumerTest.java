package org.luvx.kafka.consumer;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

class ClientConsumerTest extends ApplicationTests {
    @Autowired
    private OldClientConsumer oldConsumer;

    @Test
    void listen() {
        while (true) {
            oldConsumer.get();
        }
    }
}