package org.luvx.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.rabbitmq.producer.MQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:spring-rabbitmq.xml"})
public class TestQueue
{
    @Autowired
    MQProducer mqProducer;

    private static final String QUEUE_KEY = "rabbit";

    @Test
    public void send()
    {
        String message = "hello rabbitMQ!";
//        Map<String,Object> msg = new HashMap<String,Object>();
//        msg.put("data","hello,rabbmitmq!");
        mqProducer.sendDataToQueue(QUEUE_KEY,message);
    }
}