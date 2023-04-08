package org.luvx.boot.tools.web.base.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.web.base.spring.listener.event.MyEvent1;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AA {
    @EventListener
    public void abc(MyEvent1 event) {
        log.info("线程-【{}】 => 监听器-【MyListenerTwo】 => 监听到的事件-【{}】",
                Thread.currentThread().getName(), event);
    }
}
