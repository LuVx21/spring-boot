package org.luvx._new;

import org.junit.Test;
import org.luvx.ApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx._new
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/12/2 11:11
 */
public class ProducerTest extends ApplicationTests {

    @Autowired
    LogProducer producer;

    @Test
    public void send() throws InterruptedException {
        for (; ; ) {
            producer.send("msg -> " + LocalDateTime.now());
            Thread.sleep(5 * 1000);
        }
    }
}
