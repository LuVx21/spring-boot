/*
 * This file is generated by jOOQ.
 */
package org.luvx.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User implements Serializable {

    private static final long serialVersionUID = 2095107762;

    private Long    userId;
    private Integer age;
    private String  password;
    private String  userName;

    public User() {}

    public User(User value) {
        this.userId = value.userId;
        this.age = value.age;
        this.password = value.password;
        this.userName = value.userName;
    }

    public User(
        Long    userId,
        Integer age,
        String  password,
        String  userName
    ) {
        this.userId = userId;
        this.age = age;
        this.password = password;
        this.userName = userName;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(userId);
        sb.append(", ").append(age);
        sb.append(", ").append(password);
        sb.append(", ").append(userName);

        sb.append(")");
        return sb.toString();
    }
}
