package org.luvx.tools.base.listener;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;
import org.luvx.tools.web.base.spring.listener.enums.EventType;
import org.luvx.tools.web.base.spring.listener.event.MyEvent1;
import org.luvx.tools.web.base.spring.listener.event.MyEvent1Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.context.WebApplicationContext;

class MyListenerTest extends BaseAppTests {
    @Autowired
    private WebApplicationContext     webApplicationContext;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    void m1() {
        MyEvent1Data data = new MyEvent1Data();
        data.setId(10000L);
        MyEvent1 event = new MyEvent1(EventType.CREATE, data);
        webApplicationContext.publishEvent(event);
    }

    @Test
    void m2() {
        MyEvent1Data data = new MyEvent1Data();
        data.setId(10001L);
        MyEvent1 event = new MyEvent1(EventType.UPDATE, data);
        publisher.publishEvent(event);
    }

    @Test
    void m3() {
        MyEvent1Data data = new MyEvent1Data();
        data.setId(10003L);
        MyEvent1 event = new MyEvent1(EventType.DELETE, data);
        publisher.publishEvent(event);
    }
}