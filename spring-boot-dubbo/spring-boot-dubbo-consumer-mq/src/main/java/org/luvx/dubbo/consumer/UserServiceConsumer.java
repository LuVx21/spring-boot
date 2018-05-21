package org.luvx.dubbo.consumer;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceConsumer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendtorabbit() {
        String message = "hello rabbit!";
        System.out.println("发送消息内容: " + message);
        this.rabbitTemplate.convertAndSend("LuVx-TopicExchange", "rabbit.1", message);
    }

    public void sendtofish() {
        String message = "hello fish!";
        System.out.println("发送消息内容: " + message);
        this.rabbitTemplate.convertAndSend("LuVx-TopicExchange", "2.fish", message);
    }
}
