package org.luvx.demo;

import org.junit.Test;
import org.luvx.ApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: org.luvx.demo
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/29 17:14
 */
public class DemoTest extends ApplicationTests {
    @Autowired
    Demo.UserLogProducer producer;

    @Test
    public void run01() throws Exception {
        for (int i = 0; i < 10; i++) {
            producer.send("消息" + i);
        }
        Thread.sleep(2 * 1000);
    }
}