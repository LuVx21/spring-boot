package org.luvx.boot.mars.web.base.spring.listener.event;

import org.luvx.boot.common.listener.base.BaseEvent;
import org.luvx.boot.mars.web.base.spring.listener.enums.EventType;

public class MyEvent1 extends BaseEvent<EventType, MyEvent1Data> {
    public MyEvent1(EventType type, MyEvent1Data data) {
        super(type, data);
    }
}