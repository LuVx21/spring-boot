package org.luvx.boot.mybatis.mapper.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.luvx.boot.mybatis.mapper.common.annotations.Table;
import org.luvx.boot.mybatis.mapper.common.annotations.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table("user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    private LocalDateTime birthday;
    private LocalDateTime updateTime;
}