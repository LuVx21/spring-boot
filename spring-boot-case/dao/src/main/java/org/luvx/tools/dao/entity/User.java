package org.luvx.tools.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class User {
    @Id
    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    private LocalDateTime updateTime;
}
