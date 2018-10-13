package org.luvx.service;


import org.luvx.entity.User;

import java.util.ArrayList;
import java.util.Date;

public interface TestBean {

    ArrayList<User> getDatalist();

    void setDatalist(ArrayList<User> list);

    void addData(String title, Date time, String memo);

    void removeData(int i);

    String toString();
}