package org.luvx.pattern.Proxy;

/**
 * 假设此类不可修改和直接访问
 * 但可以通过代理的方式进行访问
 * Cglib动态代理时,可以不实现接口
 */

public class LogPrinter implements Printable {
    @Override
    public void printlog() {
        System.out.println("打印Log...");
    }
}
