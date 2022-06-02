package org.luvx.boot.mongo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@ToString
public class User  {
    @MongoId
    private Long id;
    private String userName;
    private String password;
    private int age;
}
