package org.luvx.demo;

import org.junit.Test;
import org.luvx.ApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: org.luvx.demo
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/29 17:14
 */
public class UserLogProducerTest extends ApplicationTests {
    @Autowired
    UserLogProducer producer;
    @Autowired
    UserLogConsumer consumer;

    @Test
    public void init() {
        for (int i = 0; i < 10; i++) {
            producer.sendLog(String.valueOf(i));
        }
    }

    @Test
    public void run00() {
        for (int i = 0; i < 10; i++) {
            producer.sendLog(i + "");
        }
    }
}