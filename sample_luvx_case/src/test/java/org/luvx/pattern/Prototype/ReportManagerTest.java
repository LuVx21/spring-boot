package org.luvx.pattern.Prototype;

import org.junit.Test;

public class ReportManagerTest {
    @Test
    public void createTest() {
        ReportManager pmanager = ReportManager.getReportManager();

        Document doc1, doc2, doc3, doc4;


        doc1 = pmanager.getDocument("Report");
//              doc1.display();
        doc2 = pmanager.getDocument("Report");
//              doc2.display();
        System.out.println(doc1 == doc2);

        doc3 = pmanager.getDocument("ReportMonth");
//              doc3.display();
        doc4 = pmanager.getDocument("ReportMonth");
//              doc4.display();
        System.out.println(doc3 == doc4);
    }
}
