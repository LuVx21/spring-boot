package org.luvx.boot.mul.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class User {
    private Long    id;
    private String  userName;
    private String  password;
    private Integer age;

    @Tolerate
    public User() {
    }
}