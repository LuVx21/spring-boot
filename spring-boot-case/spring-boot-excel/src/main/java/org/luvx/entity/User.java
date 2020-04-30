package org.luvx.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String userName;
    private String passWord;
    private Integer age;
    private List<Article> articles;
}