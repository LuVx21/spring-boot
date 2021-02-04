package org.luvx.schedule.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.utils.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Ren, Xie
 */
@Slf4j
@Configuration
public class DynamicTaskScheduler1 implements SchedulingConfigurer {
    private       ScheduledTaskRegistrar          taskRegistrar;
    private       Set<ScheduledFuture<?>>         scheduledFutures = null;
    private final Map<String, ScheduledFuture<?>> taskFutures      = new ConcurrentHashMap<>();

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
    }

    @SuppressWarnings("unchecked")
    private Set<ScheduledFuture<?>> getScheduledFutures() {
        if (scheduledFutures == null) {
            try {
                scheduledFutures = (Set<ScheduledFuture<?>>) BeanUtils.getProperty(taskRegistrar, "scheduledTasks");
            } catch (NoSuchFieldException e) {
                throw new SchedulingException("not found scheduledFutures field.");
            }
        }
        return scheduledFutures;
    }

    /**
     * 添加任务
     */
    public void addTriggerTask(DynamicTask dynamicTask) {
        String taskId = dynamicTask.getDynamicTaskId();
        if (taskFutures.containsKey(taskId)) {
            log.warn("定时任务已存在:{}", taskId);
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        TaskScheduler scheduler = taskRegistrar.getScheduler();
        Trigger trigger = dynamicTask.getTrigger();
        ScheduledFuture<?> future = scheduler.schedule(dynamicTask.getRunnable(), trigger);
        getScheduledFutures().add(future);
        taskFutures.put(taskId, future);
    }

    /**
     * 取消任务
     */
    public void cancelTriggerTask(String taskId) {
        ScheduledFuture<?> future = taskFutures.get(taskId);
        if (future != null) {
            future.cancel(true);
        }
        getScheduledFutures().remove(future);
        taskFutures.remove(taskId);
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
        return taskFutures.keySet();
    }

    /**
     * 是否存在任务
     */
    public boolean hasTask(String taskId) {
        return this.taskFutures.containsKey(taskId);
    }
}
