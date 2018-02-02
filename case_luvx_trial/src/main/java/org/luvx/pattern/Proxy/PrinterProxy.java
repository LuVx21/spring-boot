package org.luvx.pattern.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * printer代理类
 */
public class PrinterProxy implements Printer {


    final LogPrinter logPrinter = new LogPrinter();

    /**
     * 静态代理
     * 必须需要代理类的存在,
     * 当委托类很多时,会存在大量代理类
     */
    @Override
    public void printlog() {
        printBefore();
        logPrinter.printlog();
        printAfter();
    }

    /**
     * 动态代理
     * 获取代理对象
     * 使用此方法不需要代理类必须存在
     *
     * @param methodName 欲增强功能的方法名
     * @return
     */
    public Printer getPrinterProxy(String methodName) {
        Printer logPrinterProxy = (Printer) Proxy.newProxyInstance(LogPrinter.class.getClassLoader(), new Class[]{Printer.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 对所有方法都进行加强,则没有此if条件
                if (methodName.equals(method.getName())) {
                    printBefore();
                    Object obj = method.invoke(logPrinter, args);
                    printAfter();
                    return obj;
                }
                return method.invoke(logPrinter, args);
            }
        });
        return logPrinterProxy;
    }

    /**
     * 打印前操作
     */
    private void printBefore() {
        System.out.println("打印前操作...");
    }

    /**
     * 打印后操作
     */
    private void printAfter() {
        System.out.println("打印后操作...");
    }
}
