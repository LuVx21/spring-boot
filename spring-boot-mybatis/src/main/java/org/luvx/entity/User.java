package org.luvx.entity;

import lombok.*;
import org.luvx.common.BaseQueryEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long          id;
    private String        userName;
    private String        password;
    private Integer       age;
    private List<Article> articles;

    public User(Long id, String userName, String password, Integer age) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.age = age;
    }

    public User(String userName, String password, Integer age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
}