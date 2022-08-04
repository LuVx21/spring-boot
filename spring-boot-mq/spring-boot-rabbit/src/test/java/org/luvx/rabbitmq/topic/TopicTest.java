package org.luvx.rabbitmq.topic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.rabbit.topic.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TopicTest {

    @Autowired
    private Producer producer;

    @Test
    public void run01() {
        producer.sendtorabbit();
        producer.sendtofish();
        producer.sendall();
        producer.sendallinvaild();
    }
}
