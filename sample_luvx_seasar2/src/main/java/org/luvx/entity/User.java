package org.luvx.entity;

import java.util.Date;

public class User {
    private String title;
    private Date time;
    private String memo;

    public User() {
    }

    public User(String title, Date time, String memo) {
        this.title = title;
        this.time = time;
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.util.Date getTime() {
        return time;
    }

    public void setTime(java.util.Date time) {
        this.time = time;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Title:" + title + ",Date:" + time + ",Memo:" + memo;
    }

}
