package org.luvx.schedule.v2.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.luvx.schedule.v2.entity.TaskEntity;

import java.time.Instant;
import java.util.Objects;

/**
 * 等待调度的任务, 可以认为是任务的每个执行
 *
 * @author Ren, Xie
 */
@Getter
@Setter
@ToString
public class WaitScheduledTask {
    /**
     * 执行id
     */
    private       Long         execId;
    /**
     * 执行时间
     */
    private       Instant      execTime;
    private final TaskEntity   entity;
    private final TaskRunnable taskRunnable;

    public WaitScheduledTask(Instant execTime, TaskEntity entity, TaskRunnable taskRunnable) {
        this.execTime = execTime;
        this.entity = entity;
        this.taskRunnable = taskRunnable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WaitScheduledTask that = (WaitScheduledTask) o;
        return Objects.equals(execId, that.execId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(execId);
    }
}
