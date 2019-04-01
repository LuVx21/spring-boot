package org.luvx.module.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx.module.log.entity
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/28 10:49
 */
@TableName("sys_log")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class LogPO extends Model<LogPO> implements Serializable {
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;
    private String userId;
    private String userName;
    private String operation;
    private Integer time;
    private String method;
    private String params;
    private String ip;
    private LocalDateTime createTime;

    @Override
    protected Serializable pkVal() {
        return this.logId;
    }
}
