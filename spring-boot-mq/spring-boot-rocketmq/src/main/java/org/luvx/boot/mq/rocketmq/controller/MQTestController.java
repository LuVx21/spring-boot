package org.luvx.boot.mq.rocketmq.controller;

import javax.annotation.Resource;

import org.luvx.boot.mq.rocketmq.producer.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocket")
public class MQTestController {

    @Resource
    private Producer producer;

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String message) {
        producer.sendMessage("TestTopic", message);
        return "消息发送完成";
    }
}