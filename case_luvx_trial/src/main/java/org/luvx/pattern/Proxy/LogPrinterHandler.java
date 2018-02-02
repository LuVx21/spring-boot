package org.luvx.pattern.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 动态代理类只能代理接口(不支持抽象类)
 */

public class LogPrinterHandler implements InvocationHandler {

    private Object targetObject;

    public Object newProxyInstance(Object targetObject) {
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 对所有方法都进行加强,则没有此if条件
        if ("printlog".equals(method.getName())) {
            printBefore();
            Object obj = method.invoke(targetObject, args);
            printAfter();
            return obj;
        }
        return method.invoke(targetObject, args);
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
