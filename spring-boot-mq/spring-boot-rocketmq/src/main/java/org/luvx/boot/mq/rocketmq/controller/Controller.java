package org.luvx.boot.mq.rocketmq.controller;

import org.luvx.boot.mq.rocketmq.config.ConstValue;
import org.luvx.boot.mq.rocketmq.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocket")
public class Controller {
    @Autowired
    private Producer producer;

    @GetMapping("/sendString")
    public String sendString(@RequestParam("message") String message) {
        producer.sendString(ConstValue.STRING_TOPIC, message);
        return "消息发送完成";
    }

    @GetMapping("/sendDelay")
    public String sendDelay(@RequestParam("message") String message) {
        producer.sendDelay(ConstValue.STRING_TOPIC, message + "测试");
        return "消息发送完成";
    }

    @GetMapping("/sendUser")
    public String sendUser(@RequestParam("message") String message) {
        producer.sendUser(ConstValue.STRING_TOPIC, message);
        return "消息发送完成";
    }
}
