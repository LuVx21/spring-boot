package org.luvx.dao;


import org.luvx.entity.DataBean;

import java.util.ArrayList;

public interface TestBean {

    ArrayList<DataBean> getDatalist();

    void setDatalist(ArrayList<DataBean> list);

    void addData(String title, java.util.Date time, String memo);

    void removeData(int i);

    String toString();
}