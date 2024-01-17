package org.luvx.schedule.v1.serivce;

import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.v1.dynamic.DynamicTask;
import org.luvx.schedule.v1.dynamic.DynamicTaskScheduler;
import org.luvx.schedule.v1.dynamic.TaskRunnable;
import org.luvx.schedule.v1.entity.TaskEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * @author Ren, Xie
 */
@Slf4j
@Service
public class Service1 {
    @Resource
    private DynamicTaskScheduler dynamicTaskScheduler;

    public void addTriggerTask(@NonNull TaskEntity entity) {
        String cron = entity.getCron();
        TaskRunnable runnable = new TaskRunnable(entity);
        DynamicTask dynamicTask = new DynamicTask(runnable, cron);
        dynamicTaskScheduler.addTriggerTask(dynamicTask);
    }
}