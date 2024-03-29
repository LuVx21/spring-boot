package org.luvx.boot.jdbc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class User {
    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    private LocalDateTime birthday;
    private LocalDateTime updateTime;
}
