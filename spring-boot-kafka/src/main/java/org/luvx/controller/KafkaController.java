package org.luvx.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.common.entity.User;
import org.luvx.service.KafkaConsumerService;
import org.luvx.service.KafkaProducerService;
import org.luvx.service.OldKafkaConsumerService;
import org.luvx.service.OldKafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducerService    producerService;
    @Autowired
    private OldKafkaProducerService oldProducerService;
    @Autowired
    private OldKafkaConsumerService oldConsumerService;

    /**
     * 生产消息
     */
    @GetMapping("/send")
    void send() {
        long l = System.currentTimeMillis();
        User user = User.builder()
                .userId(l)
                .userName(String.valueOf(LocalDateTime.now()))
                .age((int) (l % 100))
                .build();
        producerService.send(user);
        // producerService.send1(user);
        // oldProducerService.send(user);
        // oldProducerService.send1(user);
    }

    /**
     * 消费消息
     */
    @GetMapping("get")
    void get() {
        oldConsumerService.get();
    }
}
