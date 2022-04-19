package org.luvx.app.config;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.Resource;

import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TaskScheduler {
    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final Map<Long, ScheduledFuture<?>> id2FutureMap = Maps.newConcurrentMap();

    public void addTriggerTask(long taskId, String cron, Runnable task) {
        if (id2FutureMap.containsKey(taskId)) {
            log.warn("定时任务已存在:{}", taskId);
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        CronTrigger trigger = new CronTrigger(cron);
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(task, trigger);
        id2FutureMap.put(taskId, future);
        log.info("add cron task:{} - {}", taskId, cron);
    }

    /**
     * 取消任务
     */
    public void cancelTriggerTask(Long taskId) {
        ScheduledFuture<?> future = id2FutureMap.get(taskId);
        if (future != null) {
            future.cancel(true);
        }
        id2FutureMap.remove(taskId);
    }

    public void cancelAll() {
        id2FutureMap.forEach((taskId, future) -> {
            if (future != null) {
                future.cancel(true);
            }
            id2FutureMap.remove(taskId);
        });
    }

    /**
     * 重置任务
     */
    public void resetTriggerTask(Long taskId, String cron, Runnable task) {
        cancelTriggerTask(taskId);
        addTriggerTask(taskId, cron, task);
    }

    /**
     * 任务编号
     */
    public Set<Long> taskIds() {
        return id2FutureMap.keySet();
    }

    /**
     * 是否存在任务
     */
    public boolean hasTask(Long taskId) {
        return this.id2FutureMap.containsKey(taskId);
    }
}
