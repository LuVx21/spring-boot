package org.luvx.pattern.Prototype;

import org.junit.Test;

public class ReportTest {

    /**
     * 带有附件的周报,深复制
     */
    @Test
    public void createTest() {
        Picture Picture = new Picture();
        ReportWeekDeep report = new ReportWeekDeep();
        ReportWeekDeep reportNew = null;
        report.setPicture(Picture);
        try {
            reportNew = report.deepClone();
        } catch (Exception e) {
            System.err.println("克隆失败！");
        }

        System.out.println(report == reportNew);
        System.out.println(report.getPicture() == reportNew.getPicture());

    }

    /**
     * 带有附件的周报,浅复制
     */
    @Test
    public void createTest0() {
        Picture picture = new Picture();
        picture.setName("风景");
        ReportWeek report = new ReportWeek();
        report.setName("ren");
        report.setDate("第1周");
        report.setLog("干活");
        report.setPicture(picture);
        ReportWeek reportNew = report.cloneReport1();

        System.out.println(report == reportNew);
        System.out.println(report.getPicture() == reportNew.getPicture());

    }


    @Test
    public void createTest1() {
        ReportWeek report = new ReportWeek();
        report.setName("ren");
        report.setDate("第1周");
        report.setLog("干活");
        ReportWeek reportNew = report.cloneReport();
        reportNew.setDate("第2周");
        System.out.println(reportNew.getName());
        System.out.println(reportNew.getDate());
        System.out.println(reportNew.getLog());
    }

    @Test
    public void createTest2() {
        ReportWeek report = new ReportWeek();
        report.setName("ren");
        report.setDate("第1周");
        report.setLog("干活");
        ReportWeek reportNew = report.cloneReport1();
        reportNew.setDate("第2周");
        System.out.println(reportNew.getName());
        System.out.println(reportNew.getDate());
        System.out.println(reportNew.getLog());
    }
}
