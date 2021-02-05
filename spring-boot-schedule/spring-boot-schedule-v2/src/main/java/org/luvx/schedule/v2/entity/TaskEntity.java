package org.luvx.schedule.v2.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * 任务配置
 *
 * @author Ren, Xie
 */
@Getter
@Setter
@ToString
@TableName(value = "task")
public class TaskEntity implements Serializable {
    @TableId(value = "id")
    private String id;
    private String cron;
    @TableField(value = "`sql`")
    private String sql;
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
