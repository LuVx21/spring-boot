package org.luvx.pattern.structural.Proxy.dynamic;

import org.luvx.pattern.structural.Proxy.Aspect;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 动态代理:Cglib代理
 * <p>
 * jdk的动态代理要求目标对象必须实现一个或多个接口
 * 若要对一个没有实现接口的单独对象进行代理可以使用Cglib代理
 * 目标类不能final
 * 目标类的方法有final/static修饰,则不被增强
 * <p>
 */
public class CglibProxy implements MethodInterceptor {

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
        Aspect.printBefore();
        Object interceptor = method.invoke(target, args);
        Aspect.printAfter();
        return interceptor;
    }
}
