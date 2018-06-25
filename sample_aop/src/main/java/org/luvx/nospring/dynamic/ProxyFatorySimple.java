package org.luvx.nospring.dynamic;

import org.luvx.nospring.aspect.PrinterAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂类,基于JDK实现的动态代理
 * 简单版
 */
public class ProxyFatorySimple {

    private static final PrinterAspect aspect = new PrinterAspect();

    public static Object newProxyInstance(Object targetObject) {
        InvocationHandler hander = (Object proxy, Method method, Object[] args) -> {
            aspect.before();
            Object aaa = method.invoke(targetObject, args);
            aspect.after();
            return aaa;
        };

        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), hander);
    }
}