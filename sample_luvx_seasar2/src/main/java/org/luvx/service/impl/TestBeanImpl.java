package org.luvx.service.impl;

import org.luvx.entity.User;
import org.luvx.service.TestBean;

import java.util.ArrayList;
import java.util.Date;

public class TestBeanImpl implements TestBean {

    private ArrayList<User> datalist;

    public TestBeanImpl() {
        datalist = new ArrayList<User>();
    }

    @Override
    public ArrayList<User> getDatalist() {
        return datalist;
    }

    @Override
    public void setDatalist(ArrayList<User> list) {
        datalist = list;
    }

    @Override
    public void addData(String title, Date time, String memo) {
        datalist.add(new User(title, time, memo));

    }

    @Override
    public void removeData(int i) {
        datalist.remove(i);
    }

    @Override
    public String toString() {
        String result = "<table border=\" 1 \">";

        for (User bean : datalist) {
            result += "<tr>";
            result += "<td>" + bean.getTitle() + "</td>";
            result += "<td>" + bean.getTime().toString() + "</td>";
            result += "<td>" + bean.getMemo() + "</td>";
            result += "</tr>";
        }
        result += "</table>";
        return result;
    }

}
