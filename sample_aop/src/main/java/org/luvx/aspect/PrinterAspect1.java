package org.luvx.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 实现
 */
public class PrinterAspect1 implements MethodInterceptor {

    public Object invoke(MethodInvocation mi) throws Throwable {
        System.out.println("打印前......");
        Object obj = mi.proceed();

        System.out.println("打印后......");
        return obj;
    }
}