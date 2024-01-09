package org.luvx.boot.mq.pulsar.config;

import lombok.*;

@Getter
@Setter
@ToString
public class User {
    private Long    id;
    private String  userName;
    private String  password;
    private Integer age;
}
