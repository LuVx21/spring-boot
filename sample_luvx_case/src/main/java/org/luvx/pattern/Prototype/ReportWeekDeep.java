package org.luvx.pattern.Prototype;

import java.io.*;

/**
 * 原型模式
 * 模拟周报
 * 深复制
 */
public class ReportWeekDeep implements Serializable {

    private String name;
    private String date;
    private String log;
    private Picture picture;

    //使用序列化技术实现深克隆
    public ReportWeekDeep deepClone() throws IOException, ClassNotFoundException, OptionalDataException {
        //将对象写入流中
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);

        //将对象从流中取出
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (ReportWeekDeep) ois.readObject();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setLog(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
