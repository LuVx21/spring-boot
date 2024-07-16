package org.luvx.boot.mars.dao.entity;

import io.mybatis.provider.Entity.Column;
import io.mybatis.provider.Entity.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Table("user")
@Document("user")
public class User {
    @MongoId
    @Column(id = true)
    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    private LocalDateTime birthday;
    private LocalDateTime updateTime;
}
