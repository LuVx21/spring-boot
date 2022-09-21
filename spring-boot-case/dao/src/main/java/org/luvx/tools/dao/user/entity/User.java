package org.luvx.tools.dao.user.entity;

import io.mybatis.provider.Entity.Column;
import io.mybatis.provider.Entity.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Table("user")
public class User {
    @Column(id = true)
    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    private LocalDateTime updateTime;
}
