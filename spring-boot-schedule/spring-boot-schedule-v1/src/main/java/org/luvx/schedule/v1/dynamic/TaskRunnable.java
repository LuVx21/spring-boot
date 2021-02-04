package org.luvx.schedule.v1.dynamic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.v1.entity.TaskEntity;

/**
 * @author Ren, Xie
 */
@Slf4j
public class TaskRunnable implements Runnable {
    @Getter
    private final TaskEntity taskInfo;

    public TaskRunnable(TaskEntity taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public void run() {
        log.info("执行任务({}) sql:{}", taskInfo.getId(), taskInfo.getSql());
    }
}
