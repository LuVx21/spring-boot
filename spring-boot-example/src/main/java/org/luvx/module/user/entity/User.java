package org.luvx.module.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import org.luvx.common.base.BaseQueryEntity;

import java.io.Serializable;

/**
 * @ClassName: org.luvx.common.base
 * @Description: 实体
 * @Author: Ren, Xie
 * @Date: 2019/3/11 14:50
 */
@Getter
@Setter
@ToString
@Builder
public class User extends BaseQueryEntity
        implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long    userId;
    private String  userName;
    private String  password;
    private Integer age;
}