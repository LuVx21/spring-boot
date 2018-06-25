package org.luvx.nospring.aspect;

public class PrinterAspect {

    public void before() {
        System.out.println("打印前......");
    }

    public void after() {
        System.out.println("打印后......");
    }
}
