package org.luvx.common.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class User extends BaseQueryEntity {
    private Long id;
    private String userName;
    private String password;
    private Integer age;
    private LocalDateTime updateTime;

    /**
     * 一对一
     */
    private Article article;
    /**
     * 一对多
     */
    private List<Article> articles;

    @Tolerate
    public User() {
    }
}