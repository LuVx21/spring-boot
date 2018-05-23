package org.luvx.nospring.dynamic;

import org.luvx.nospring.aspect.PrinterAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂类,基于JDK实现的动态代理
 * 实质是一个InvocationHandler
 */
public class ProxyFatory implements InvocationHandler {

    private static final PrinterAspect aspect = new PrinterAspect();

    private Object targetObject;

    private ProxyFatory() {
    }

    private static class ProxyFatoryHolder {
        private static final ProxyFatory INSTANCE = new ProxyFatory();
    }

    public static final ProxyFatory getProxyFatory() {
        return ProxyFatoryHolder.INSTANCE;
    }

    public Object newProxyInstance(Object targetObject) {
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        aspect.before();
        Object obj = method.invoke(targetObject, args);
        aspect.after();
        return obj;
    }
}