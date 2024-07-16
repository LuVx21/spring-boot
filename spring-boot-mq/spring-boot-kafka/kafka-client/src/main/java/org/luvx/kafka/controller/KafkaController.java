package org.luvx.kafka.controller;


import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.luvx.kafka.common.entity.User;
import org.luvx.kafka.config.KafkaConsumerContext;
import org.luvx.kafka.config.KafkaDynamicConsumerFactory;
import org.luvx.kafka.producer.ClientProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Resource
    private ClientProducer              clientProducer;
    @Resource
    private KafkaDynamicConsumerFactory factory;
    @Resource
    private String[]                    kafkaTopicName;

    @GetMapping("/send")
    public String send() {
        long l = System.currentTimeMillis();
        User user = User.builder()
                .userId(l).userName(String.valueOf(LocalDateTime.now())).age((int) (l % 100))
                .build();
        clientProducer.send(user);
        return "发送完成！";
    }

    @GetMapping("/create/{groupId}")
    public String create(@PathVariable String groupId) throws ClassNotFoundException {
        KafkaConsumer<String, String> consumer = factory.createConsumer(kafkaTopicName[0], groupId);
        KafkaConsumerContext.addConsumerTask(groupId, consumer);
        return "创建成功！";
    }

    @GetMapping("/remove/{groupId}")
    public String remove(@PathVariable String groupId) {
        KafkaConsumerContext.removeConsumerTask(groupId);
        return "移除成功！";
    }
}
