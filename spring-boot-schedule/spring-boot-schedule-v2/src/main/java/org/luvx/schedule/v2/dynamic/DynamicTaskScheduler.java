package org.luvx.schedule.v2.dynamic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.v2.entity.TaskEntity;
import org.luvx.schedule.v2.pojo.TaskRunnable;
import org.luvx.schedule.v2.pojo.WaitScheduledTask;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ren, Xie
 */
@Slf4j
@Component
public class DynamicTaskScheduler {
    @Resource
    private       ScheduledThreadPoolExecutor     scheduledThreadPoolExecutor;
    @Resource
    private       ThreadPoolTaskScheduler         threadPoolTaskScheduler;
    @Getter
    private final Map<String, ScheduledFuture<?>> id2FutureMap = new ConcurrentHashMap<>();

    /**
     * false: 执行过程中不中断
     * true: 执行过程中可中断
     */
    private final boolean mayInterruptIfRunning = false;

    public void schedule(WaitScheduledTask waitScheduledTask) {
        TaskRunnable task = waitScheduledTask.getTaskRunnable();
        TaskEntity entity = task.getEntity();
        String taskId = entity.getId();
        if (id2FutureMap.containsKey(taskId)) {
            log.warn("定时任务已存在:{}", taskId);
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        Instant startTime = waitScheduledTask.getExecTime();
        ScheduledFuture<?> future;
        if (mayInterruptIfRunning) {
            long delay = startTime.toEpochMilli() - System.currentTimeMillis();
            future = scheduledThreadPoolExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
        } else {
            future = threadPoolTaskScheduler.schedule(task, startTime);
        }
        id2FutureMap.put(taskId, future);
    }

    public void cancelTask(String taskId) {
        ScheduledFuture<?> future = id2FutureMap.get(taskId);
        if (future != null) {
            future.cancel(true);
        }
        id2FutureMap.remove(taskId);
    }

    public void resetTriggerTask(WaitScheduledTask waitScheduledTask) {
        String taskId = waitScheduledTask.getEntity().getId();
        cancelTask(taskId);
        schedule(waitScheduledTask);
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
        return id2FutureMap.containsKey(taskId);
    }

    /**
     * 删除掉已经执行的任务
     *
     * @param taskId
     */
    public void remove(String taskId) {
        id2FutureMap.remove(taskId);
    }
}
