package org.luvx.demo;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserLogProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendLog(String userid) {
        UserLog userLog = new UserLog();
        userLog.setUsername("foobar").setUserId(userid).setState("0");
        log.info("发送用户日志数据:{}", userLog);
        kafkaTemplate.send("user-log", userLog.toString());
    }

    @Data
    @Accessors(chain = true)
    public class UserLog {
        private String userId;
        private String username;
        private String state;
    }
}
