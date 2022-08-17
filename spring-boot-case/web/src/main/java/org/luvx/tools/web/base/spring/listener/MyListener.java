package org.luvx.tools.web.base.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyListener implements ApplicationListener<MyListener.MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("事件:{}", event.toString());
    }

    public static class MyEvent extends ApplicationEvent {
        public MyEvent(Object source) {
            super(source);
        }
    }
}
