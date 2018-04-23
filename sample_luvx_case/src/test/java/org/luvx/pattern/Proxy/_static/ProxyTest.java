package org.luvx.pattern.Proxy._static;

import org.junit.Test;

public class ProxyTest {
    /**
     * 静态代理
     * 标准的使用结构
     */
    @Test
    public void run00() {
        new PrinterStaticProxy().printlog();
    }

    /**
     * getPrinterProxy设置为public
     * 可以动态设置增强的方法
     */
    @Test
    public void run01() {
        String methodName = "printlog";
        new PrinterStaticProxy().getPrinterProxy(methodName).printlog();
    }

    /**
     * 不增强方法
     */
    @Test
    public void run02() {
        String methodName = "printlogNo";
        new PrinterStaticProxy().getPrinterProxy(methodName).printlog();
    }

}
