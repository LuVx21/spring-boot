package org.luvx.interceptor;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

public class MeasurementInterceptorTest {

    @Test
    public void invoke() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new Object());
        proxyFactory.addAdvice(new MeasurementInterceptor());

        Object proxy = proxyFactory.getProxy();
        Object obj = proxy;
        obj.toString();
    }
}