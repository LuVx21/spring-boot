package org.luvx.module.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName: org.luvx.common.base
 * @Description: 实体
 * @Author: Ren, Xie
 * @Date: 2019/3/11 14:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    private String userName;
    private String password;
    private Integer age;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}