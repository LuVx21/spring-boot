package org.luvx.boot.mq.pulsar.controller;

import org.luvx.boot.mq.pulsar.config.User;
import org.luvx.boot.mq.pulsar.producer.PulsarProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/pulsar")
public class Controller {
    @Autowired
    private PulsarProducer producer;

    @GetMapping("/sendString")
    public String sendString(@RequestParam("message") String message) throws Exception {
        producer.sendString2Topic(message + " + this is renxie + " + LocalDateTime.now());

        return "消息发送完成";
    }

    @GetMapping("/sendUser")
    public User sendUser(@RequestParam("message") String message) throws Exception {
        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setUserName("name:" + user.getId());
        user.setPassword(message);
        producer.sendUser2Topic(user);

        return user;
    }
}