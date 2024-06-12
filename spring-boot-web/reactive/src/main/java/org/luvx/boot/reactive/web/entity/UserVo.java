package org.luvx.boot.reactive.web.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVo {
    private long   id;
    private String userName;
    private String password;
}