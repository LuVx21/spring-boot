package org.luvx.schedule.v1.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Ren, Xie
 */
@Slf4j
@Component
public class DynamicTaskScheduler {
    @Resource
    private       ThreadPoolTaskScheduler         threadPoolTaskScheduler;
    private final Map<String, ScheduledFuture<?>> id2FutureMap = new ConcurrentHashMap<>();

    /**
     * 添加任务
     */
    public void addTriggerTask(DynamicTask dynamicTask) {
        String taskId = dynamicTask.getDynamicTaskId();
        if (id2FutureMap.containsKey(taskId)) {
            log.warn("定时任务已存在:{}", taskId);
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        Trigger trigger = dynamicTask.getTrigger();
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(dynamicTask.getRunnable(), trigger);
        id2FutureMap.put(taskId, future);
    }

    /**
     * 取消任务
     */
    public void cancelTriggerTask(String taskId) {
        ScheduledFuture<?> future = id2FutureMap.get(taskId);
        if (future != null) {
            future.cancel(true);
        }
        id2FutureMap.remove(taskId);
    }

    /**
     * 重置任务
     */
    public void resetTriggerTask(DynamicTask dynamicTask) {
        String taskId = dynamicTask.getDynamicTaskId();
        cancelTriggerTask(taskId);
        addTriggerTask(dynamicTask);
    }

    /**
     * 任务编号
     */
    public Set<String> taskIds() {
        return id2FutureMap.keySet();
    }

    /**
     * 是否存在任务
     */
    public boolean hasTask(String taskId) {
        return this.id2FutureMap.containsKey(taskId);
    }
}
