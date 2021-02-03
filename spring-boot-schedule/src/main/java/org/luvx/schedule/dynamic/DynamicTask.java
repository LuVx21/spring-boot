package org.luvx.schedule.dynamic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
    private String expression;

    public DynamicTask(Runnable runnable, String expression, String dynamicTaskId) {
        super(runnable, expression);
        this.dynamicTaskId = dynamicTaskId;
        this.expression = expression;
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
