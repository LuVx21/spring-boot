package org.luvx.entity;

import java.io.Serializable;

/**
 * 使用redis的pojo类
 * <p>
 * 需要实现Serializable接口
 */
public class User implements Serializable {
    private long id;
    // 驼峰式命名
    private String userName;
    // 非驼峰式命名
    private String password;
    private int age;


    public User() {
    }


    public User(String userName, String password, int age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }


    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return id + " - " + userName + " - " + password + " - " + age;
    }
}

