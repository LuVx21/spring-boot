package org.luvx.boot.mongo.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document("user")
public class User {
    @MongoId
    private Long          id;
    private String        userName;
    private String        password;
    private int           age;
    private LocalDateTime birthday;
}
