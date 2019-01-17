package org.luvx.pattern.structural.Decorator;

/**
 * 具体被装饰者
 * 和装饰器接口实现同一个接口
 */

public class LogPrinter implements Printer {
    @Override
    public void printlog() {
        System.out.println("打印Log");
    }
}
