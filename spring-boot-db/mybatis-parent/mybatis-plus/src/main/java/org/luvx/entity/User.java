package org.luvx.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User {
    public static final String COL_ID    = "id";
    public static final String COL_UNAME = "user_name";
    public static final String COL_PWD   = "password";
    public static final String COL_AGE   = "age";
    public static final String COL_UTM   = "update_time";

    @TableId(value = "id", type = IdType.AUTO)
    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    private LocalDateTime updateTime;
}