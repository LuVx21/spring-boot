package org.luvx.nospring.anno;

import org.junit.Test;
import org.luvx.anno.service.impl.DocPrinter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnoTest {

    @Test
    public void run01() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext0.xml");
        DocPrinter printer = (DocPrinter) ac.getBean("docPrinter");
        printer.print();
    }
}
