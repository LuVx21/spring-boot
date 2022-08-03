package org.luvx.spring.listener;

import org.junit.jupiter.api.Test;
import org.luvx.BaseAppTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

class MyListenerTest extends BaseAppTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void publish() {
        MyEvent event = new MyEvent("测试");
        webApplicationContext.publishEvent(event);
    }
}