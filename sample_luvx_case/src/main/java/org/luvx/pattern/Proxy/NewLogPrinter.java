package org.luvx.pattern.Proxy;

/**
 * Cglib动态代理时,可以不实现接口
 */

public class NewLogPrinter {
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
