package org.luvx.nospring.dynamic;

import org.luvx.nospring.aspect.PrinterAspect;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理工厂类,基于cglib实现的动态代理
 */
public class ProxyFatoryCglib implements MethodInterceptor {

    private static final PrinterAspect aspect = new PrinterAspect();
    private Object target;

    public Object newProxyInstance(Object target) {
        this.target = target;
        Enhancer en = new Enhancer();
        en.setSuperclass(this.target.getClass());
        // 回调函数
        en.setCallback(this);
        // 子类(代理对象)
        return en.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        aspect.before();
        Object interceptor = method.invoke(target, args);
        aspect.after();
        return interceptor;
    }
}