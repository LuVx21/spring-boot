package org.luvx.pattern.Prototype;

import java.util.Hashtable;

/**
 * 原型管理器
 */
public class ReportManager {

    //定义一个Hashtable，用于存储原型对象
    private Hashtable<String, Document> ht = new Hashtable<String, Document>();
    private static ReportManager pm = new ReportManager();

    //为Hashtable增加周报月报对象
    private ReportManager() {
        ht.put("Report", new ReportWeek());
        ht.put("ReportMonth", new ReportMonth());
    }

    public void addDocument(String key, Document doc) {
        ht.put(key, doc);
    }

    public Document getDocument(String key) {
        return ((Document) ht.get(key)).cloneReport();
    }

    public static ReportManager getReportManager() {
        return pm;
    }
}
