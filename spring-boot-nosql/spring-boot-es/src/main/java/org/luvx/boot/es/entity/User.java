package org.luvx.boot.es.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Document(indexName = User.INDEX)
public class User {
    public static final String INDEX = "t_user";

    public static final String ID          = "id";
    public static final String USER_NAME   = "userName";
    public static final String PASSWORD    = "password";
    public static final String AGE         = "age";
    public static final String BIRTHDAY    = "birthday";
    public static final String REMARK      = "remark";
    public static final String ARTICLES    = "articles";
    public static final String ARTICLES_ID = "articles.id";

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
    //    TODO
//    @Field(type = FieldType.Nested)
//    private Article       mainArticle;
    @Field(type = FieldType.Nested)
    private List<Article> articles;
}