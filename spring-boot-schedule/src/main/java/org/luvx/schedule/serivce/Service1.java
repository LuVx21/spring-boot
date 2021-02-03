package org.luvx.schedule.serivce;

import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.dynamic.DynamicTask;
import org.luvx.schedule.dynamic.DynamicTaskScheduler;
import org.luvx.schedule.dynamic.pojo.TaskCallable;
import org.luvx.schedule.dynamic.pojo.TaskModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Ren, Xie
 */
@Slf4j
@Service
public class Service1 {
    @Resource
    private DynamicTaskScheduler dynamicTaskScheduler;

    public void addTriggerTask(@NonNull TaskModel model) {
        String taskId = model.getId(), sql = model.getSql(), cron = model.getCron();
        TaskCallable callable = new TaskCallable(sql);
        // ListenableFutureTask<Long> futureTask = new ListenableFutureTask<>(callable, 0L);
        // futureTask.addCallback(this::verifyTaskFinishCallback,
        //         ex -> log.warn("核验任务异常结束:{}", taskId)
        // );
        DynamicTask dynamicTask = new DynamicTask(callable, cron, taskId);
        dynamicTaskScheduler.addTriggerTask(dynamicTask);
    }

    private void verifyTaskFinishCallback(Long cnt) {
        log.info("线程: {} task1 start......: {}", Thread.currentThread().getName(), LocalDateTime.now());
        if (cnt.equals(0L)) {
            log.info("偶数:{}", cnt);
        } else {
            log.info("奇数:{}", cnt);
        }
    }
}