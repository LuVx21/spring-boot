package org.luvx.entity;

import lombok.*;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * 使用redis的pojo类
 * <p>
 * 需要实现Serializable接口
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @Nullable
    private long   id;
    /**
     * 驼峰式命名
     */
    private String userName;
    /**
     * 非驼峰式命名
     */
    private String password;
    private int    age;

    public User(String userName, String password, int age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
}
