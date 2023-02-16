package org.luvx.boot.es.entity;

import lombok.*;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@Document(indexName = "t_user")
public class User {

    @Tolerate
    public User() {
    }

    @Id
    private Long          id;
    private String        userName;
    private String        password;
    @Field
    private Integer       age;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime birthday;
    @Field(type = FieldType.Keyword)
    private String        remark;
}