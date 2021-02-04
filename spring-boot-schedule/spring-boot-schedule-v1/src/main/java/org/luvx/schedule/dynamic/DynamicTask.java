package org.luvx.schedule.dynamic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.luvx.schedule.entity.TaskEntity;
import org.springframework.scheduling.config.CronTask;

import java.util.Objects;

/**
 * @author Ren, Xie
 */
@Getter
@Setter
@ToString
public class DynamicTask extends CronTask {
    private String dynamicTaskId;

    public DynamicTask(TaskRunnable runnable, String expression) {
        super(runnable, expression);
        TaskEntity taskInfo = runnable.getTaskInfo();
        this.dynamicTaskId = taskInfo.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DynamicTask that = (DynamicTask) o;
        return Objects.equals(dynamicTaskId, that.dynamicTaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dynamicTaskId);
    }
}
