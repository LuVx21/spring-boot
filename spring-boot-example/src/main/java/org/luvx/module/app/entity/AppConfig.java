package org.luvx.module.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx.module.app.entity
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/26 11:12
 */
@TableName("sys_app_config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppConfig extends Model<AppConfig> implements Serializable {
    @TableId(value = "config_id", type = IdType.AUTO)
    private String configId;
    private String configKey;
    private String configValue;
    @TableField("is_valid")
    private Boolean valid;
    private LocalDateTime createTime;
    private String createUser;
    private LocalDateTime updateTime;
    private String updateUser;

    @Override
    protected Serializable pkVal() {
        return this.configId;
    }
}
