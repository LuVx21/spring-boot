package org.luvx.rabbit.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Reference;
import org.luvx.dubbo.service.UserService;

@Component
@RabbitListener(queues = {"rabbit"})
public class ListenerRabbit {
    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:20880")
    private UserService userService;

    @RabbitHandler
    public void process(String message) {
        System.out.println("From rabbit -> 接收消息内容:" + message);
        System.out.println(message + ":" + userService.listNames((long) 3).toString());
    }
}
