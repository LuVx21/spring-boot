package org.luvx.pattern.Proxy;

/**
 * 假设此类不可修改和直接访问
 * 但可以通过代理的方式进行访问
 * Cglib动态代理时,可以不实现接口
 */

public class LogPrinter implements Printer {
    @Override
    public void printlog() {
        System.out.println("打印Log...");
    }

    /**
     * Cglib代理用
     * static/final的方法不会被增强
     */
    public final void pringlogNo() {
        System.out.println("不加强功能的打印Log...");
    }
}
