package org.luvx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.luvx.enums.BizTypeEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User {
    public static final String COL_ID       = "id";
    public static final String COL_UNAME    = "user_name";
    public static final String COL_PWD      = "password";
    public static final String COL_AGE      = "age";
    public static final String COL_BIZ_TYPE = "biz_type";
    public static final String COL_UTM      = "update_time";

    @TableId(value = "id", type = IdType.AUTO)
    private Long          id;
    private String        userName;
    private String        password;
    // @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer       age;
    private BizTypeEnum   bizType;
    private LocalDateTime updateTime;
}