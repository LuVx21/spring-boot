package org.luvx.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

public class PrinterAspect {

    public Object testAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("打印前......");
        Object obj = joinPoint.proceed();
        System.out.println("打印后......");
        return obj;
    }

}