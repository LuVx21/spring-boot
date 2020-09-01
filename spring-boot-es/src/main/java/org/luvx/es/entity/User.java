package org.luvx.es.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * @author: Ren, Xie
 */
@Getter
@Setter
@Builder
@Document(indexName = "users")
@Data
public class User {

    @Tolerate
    public User() {
    }

    @Id
    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime birthday;
}