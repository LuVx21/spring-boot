package org.luvx.producer;

import org.luvx.entity.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserLogProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendLog(String userid) {
        UserLog userLog = new UserLog();
        userLog.setUsername("jhp").setUserid(userid).setState("0");
        System.err.println("发送用户日志数据:" + userLog);
        kafkaTemplate.send("user-log", userLog.toString());
    }
}
