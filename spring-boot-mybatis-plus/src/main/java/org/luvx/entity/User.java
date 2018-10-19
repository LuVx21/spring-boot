package org.luvx.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String passWord;
    private Integer age;

    public User() {
        super();
    }

    public User(String userName, String passWord, Integer age) {
        this.passWord = passWord;
        this.userName = userName;
        this.age = age;
    }
    
}