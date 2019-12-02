package org.luvx.old;

import org.junit.Test;
import org.luvx.ApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx.old
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 11:47
 */
public class LogProducer1Test extends ApplicationTests {
    @Autowired
    LogProducer1 producer;

    @Test
    public void send() throws InterruptedException {
        for (; ; ) {
            producer.send("msg -> " + LocalDateTime.now());
            Thread.sleep(5 * 1000);
        }
    }
}