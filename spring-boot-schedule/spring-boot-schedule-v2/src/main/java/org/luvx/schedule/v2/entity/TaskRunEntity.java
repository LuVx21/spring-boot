package org.luvx.schedule.v2.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 任务运行
 *
 * @author Ren, Xie
 */
@Getter
@Setter
@ToString
// @TableName(value = "task_run")
public class TaskRunEntity implements Serializable {
    /**
     * 执行id
     */
    // @TableId(value = "id")
    private String  id;
    /**
     * 任务配置id
     */
    private String  configTaskId;
    /**
     * 记录到秒级别
     * 同一任务在同一秒的 认为是同一次执行
     */
    private Integer execTime;
    // private String cron;
    // @TableField(value = "`sql`")
    // private String sql;
    // private String status;
    //
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) {
    //         return true;
    //     }
    //     if (o == null || getClass() != o.getClass()) {
    //         return false;
    //     }
    //     TaskRunEntity that = (TaskRunEntity) o;
    //     return Objects.equals(id, that.id);
    // }
    //
    // @Override
    // public int hashCode() {
    //     return Objects.hash(id);
    // }
}
