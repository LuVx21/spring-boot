package org.luvx;

import org.junit.Test;
import org.luvx.service.impl.DocPrinter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XMLTest {

    @Test
    public void run01() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext2.xml");
        DocPrinter printer = (DocPrinter) ac.getBean("proxyFactory");
        printer.print();
    }

    @Test
    public void run02() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext1.xml");
        DocPrinter printer = (DocPrinter) ac.getBean("docPrinter");
        printer.print();
    }

    @Test
    public void run03() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        DocPrinter printer = (DocPrinter) ac.getBean("docPrinter");
        printer.print();
    }
}
