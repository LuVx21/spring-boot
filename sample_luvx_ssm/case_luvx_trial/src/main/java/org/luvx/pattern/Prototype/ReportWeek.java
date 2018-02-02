package org.luvx.pattern.Prototype;

/**
 * 原型模式
 * 模拟周报
 * 浅复制
 */
public class ReportWeek implements Document, Cloneable {

    private String name;
    private String date;
    private String log;
    private Picture picture;


    /**
     * 使用JDK的Clone方法
     * 需要实现Cloneable接口
     */
        public ReportWeek cloneReport() {
        Object object = null;
        try {
            object = super.clone();
        } catch (CloneNotSupportedException exception) {
            System.err.println("Not support cloneable");
        }
        return (ReportWeek) object;
    }

    /**
     * 克隆方法
     *
     * @return
     */
    public ReportWeek cloneReport1() {
        //创建新对象
        ReportWeek report = new ReportWeek();
        report.setName(this.name);
        report.setDate(this.date);
        report.setLog(this.log);
        return report;
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
