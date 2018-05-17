package org.luvx.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.rabbitmq.producer.MQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tppic方式
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:spring-rabbitmq_topic.xml"})
public class TopicTest {
    @Autowired
    MQProducer mqProducer;

    private static final String ROUTING_KEY_RABBIT = "rabbit.xx.xx";
    private static final String ROUTING_KEY_FISH = "11.22.fish";
    private static final String ROUTING_KEY_NOREC = "rabbit1.33.fish1";

    @Test
    public void send() {
        String message = "hello rabbitMQ!";
        mqProducer.sendDataToQueue(ROUTING_KEY_RABBIT, message);

        String msg = "hello fish!";
        mqProducer.sendDataToQueue(ROUTING_KEY_FISH, msg);

        String msg_no_rec = "你应该接收不到这条消息!";
        mqProducer.sendDataToQueue(ROUTING_KEY_NOREC, msg_no_rec);
    }
}