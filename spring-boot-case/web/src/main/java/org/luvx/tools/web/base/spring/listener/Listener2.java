package org.luvx.tools.web.base.spring.listener;

import org.luvx.tools.web.base.spring.listener.event.MyEvent1;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Listener2 implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return MyEvent1.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Object source = event.getSource();
        log.info("smart listener:{}", JSON.toJSONString(source));
    }
}
