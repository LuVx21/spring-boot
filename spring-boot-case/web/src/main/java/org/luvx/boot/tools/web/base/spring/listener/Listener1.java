package org.luvx.boot.tools.web.base.spring.listener;

import java.util.Set;

import org.luvx.boot.common.listener.base.BaseEventListener;
import org.luvx.boot.tools.web.base.spring.listener.enums.EventType;
import org.luvx.boot.tools.web.base.spring.listener.event.MyEvent1;
import org.luvx.boot.tools.web.base.spring.listener.event.MyEvent1Data;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Listener1 extends BaseEventListener<MyEvent1, EventType, MyEvent1Data> {
    @Override
    protected void onEvent(EventType type, MyEvent1Data data) {
        switch (type) {
            case CREATE -> log.info("处理新增事件: {}", JSON.toJSONString(data));
            case UPDATE -> log.info("处理更新事件:{}", JSON.toJSONString(data));
            default -> {
            }
        }
    }

    @Override
    protected Set<EventType> listenEventType() {
        return Set.of(EventType.CREATE, EventType.UPDATE);
    }
}
