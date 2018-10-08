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

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return id + " - " + userName + " - " + password + " - " + age;
    }
}

