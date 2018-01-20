package org.luvx.pattern.Proxy;

/**
 *假设此类不可修改和直接访问
 * 但可以通过代理的方式进行访问
 */

public class LogPrinter implements Printer {
    @Override
    public void printlog() {
        System.out.println("打印Log");
    }
}
