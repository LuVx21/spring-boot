package org.luvx.rabbit.topic.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"rabbit"})
public class ListenerRabbit {

    @RabbitHandler
    public void process(String message) {
        System.out.println("From rabbit -> 接收消息内容:" + message);
    }
}


