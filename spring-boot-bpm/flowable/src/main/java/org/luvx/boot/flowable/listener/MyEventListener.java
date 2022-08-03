package org.luvx.boot.flowable.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;

import java.util.Map;

@Slf4j
public class MyEventListener extends AbstractFlowableEngineEventListener {
    /**
     * 任务创建时, 动态获取任务的负责人
     */
    @Override
    protected void taskCreated(FlowableEngineEntityEvent event) {
        Object entity = event.getEntity();
        TaskEntityImpl t = (TaskEntityImpl) entity;
        t.setAssignee("renxie");
        Map<String, Object> map = (Map<String, Object>) t.getPersistentState();
        map.put("id", t.getId());
        log.info("任务创建:{} {}", event, map);
    }

    @Override
    protected void taskAssigned(FlowableEngineEntityEvent event) {
        super.taskAssigned(event);
    }

    @Override
    protected void taskCompleted(FlowableEngineEntityEvent event) {
        Object entity = event.getEntity();
        TaskEntityImpl t = (TaskEntityImpl) entity;
        Map<String, Object> map = (Map<String, Object>) t.getPersistentState();
        map.put("id", t.getId());
        log.info("任务完成:{} {}", event, map);
    }
}
