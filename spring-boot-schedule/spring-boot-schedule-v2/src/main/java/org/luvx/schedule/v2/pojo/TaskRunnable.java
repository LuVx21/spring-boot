package org.luvx.schedule.v2.pojo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.v2.entity.TaskEntity;
import org.luvx.schedule.v2.service.TaskServiceImpl;
import org.luvx.schedule.v2.utils.ApplicationContextUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Ren, Xie
 */
@Slf4j
public class TaskRunnable implements Runnable {

    @Getter
    private final TaskEntity      entity;
    private final TaskServiceImpl taskService;

    public TaskRunnable(TaskEntity entity) {
        this.entity = entity;
        taskService = ApplicationContextUtils.getBean(TaskServiceImpl.class);
    }

    public void schedule() {
        taskService.addRunTask(entity);
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            log.info("模拟定时任务(id:{})的执行......completed {}", entity.getId(), i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
        schedule();
    }
}
