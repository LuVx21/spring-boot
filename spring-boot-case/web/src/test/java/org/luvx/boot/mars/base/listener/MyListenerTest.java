package org.luvx.boot.mars.base.listener;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.mars.BaseAppTests;
import org.luvx.boot.mars.web.base.spring.listener.enums.EventType;
import org.luvx.boot.mars.web.base.spring.listener.event.MyEvent1;
import org.luvx.boot.mars.web.base.spring.listener.event.MyEvent1Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
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
        log.info("主线程:{}", Thread.currentThread().getName());
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