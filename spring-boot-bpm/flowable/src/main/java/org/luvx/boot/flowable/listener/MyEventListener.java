package org.luvx.boot.flowable.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;
import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
import org.flowable.engine.delegate.event.impl.FlowableEntityWithVariablesEventImpl;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;

import java.util.Map;

@Slf4j
public class MyEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {
        FlowableEventType type = event.getType();
        if (type == FlowableEngineEventType.TASK_CREATED) {
            FlowableEntityEventImpl event1 = (FlowableEntityEventImpl) event;
            Object entity = event1.getEntity();
            TaskEntityImpl t = (TaskEntityImpl) entity;
            Map<String, Object> map = (Map<String, Object>) t.getPersistentState();
            map.put("id", t.getId());
            log.info("任务创建:{} {}", event, map);
        } else if (type == FlowableEngineEventType.TASK_COMPLETED) {
            FlowableEntityWithVariablesEventImpl event1 = (FlowableEntityWithVariablesEventImpl) event;
            Object entity = event1.getEntity();
            log.info("任务完成:{} {}", event, entity.getClass());
        } else {
            // log.info("MyEventListener====》onEvent:{}", event);
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
