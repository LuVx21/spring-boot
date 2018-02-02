package org.luvx.pattern.Proxy;

import org.junit.Test;

public class ProxyTest {
    /**
     * 静态代理
     * 标准的使用结构
     */
    @Test
    public void run00() {
        new PrinterProxy().printlog();
    }

    /**
     * getPrinterProxy设置为public
     * 可以动态设置增强的方法
     */
    @Test
    public void run01() {
        String methodName = "printlog";
        new PrinterProxy().getPrinterProxy(methodName).printlog();
    }

    /**
     * 不增强方法
     */
    @Test
    public void run02() {
        String methodName = "printlogNo";
        new PrinterProxy().getPrinterProxy(methodName).printlog();
    }

}
