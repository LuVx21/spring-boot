package org.luvx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@TableName(value = "user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long          userId;
    private String        userName;
    private String        password;
    private Integer       age;
    private LocalDateTime updateTime;
}