package org.luvx.pattern.structural.Proxy._static;


import org.luvx.pattern.structural.Proxy.Aspect;
import org.luvx.pattern.structural.Proxy.LogPrinter;
import org.luvx.pattern.structural.Proxy.Printable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * printer代理类
 */
public class PrinterStaticProxy implements Printable {

    final Printable logPrinter = new LogPrinter();

    /**
     * 静态代理
     * 必须需要代理类的存在,
     * 当委托类很多时,会存在大量代理类
     */
    @Override
    public void printlog() {
        Aspect.printBefore();
        logPrinter.printlog();
        Aspect.printAfter();
    }

    /**
     * 动态代理
     * 获取代理对象
     * 使用此方法不需要代理类必须存在
     *
     * @param methodName 欲增强功能的方法名
     * @return
     */
    public Printable getPrinterProxy(String methodName) {
        Printable logPrinterProxy = (Printable) Proxy.newProxyInstance(LogPrinter.class.getClassLoader(), new Class[]{Printable.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 对所有方法都进行加强,则没有此if条件
                if (methodName.equals(method.getName())) {
                    Aspect.printBefore();
                    Object obj = method.invoke(logPrinter, args);
                    Aspect.printAfter();
                    return obj;
                }
                return method.invoke(logPrinter, args);
            }
        });
        return logPrinterProxy;
    }
}
