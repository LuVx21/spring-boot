package org.luvx.dao;

import org.luvx.entity.DataBean;

import java.util.ArrayList;
import java.util.Date;

public class TestBeanImpl implements TestBean {

    private ArrayList<DataBean> datalist;

    public TestBeanImpl() {
        datalist = new ArrayList<DataBean>();
    }

    @Override
    public ArrayList<DataBean> getDatalist() {
        return datalist;
    }

    @Override
    public void setDatalist(ArrayList<DataBean> list) {
        datalist = list;

    }

    @Override
    public void addData(String title, Date time, String memo) {
        datalist.add(new DataBean(title, time, memo));

    }

    @Override
    public void removeData(int i) {
        datalist.remove(i);
    }

    @Override
    public String toString() {
        String result = "<table border=\" 1 \">";

        for (DataBean bean : datalist) {
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
