package org.luvx.entity;

import org.luvx.common.BaseQueryEntity;

import java.io.Serializable;
import java.util.List;

public class User extends BaseQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String userName;
    private String passWord;
    private Integer age;
    private List<Article> articles;

    public User() {
        super();
    }

    public User(String userName, String passWord, Integer age) {
        this.passWord = passWord;
        this.userName = userName;
        this.age = age;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}