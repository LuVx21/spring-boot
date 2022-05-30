package org.luvx.boot.mul.mybatis.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DsAspect {

    @Around("@within(DS)")
    public Object dsAround(ProceedingJoinPoint joinPoint) throws Throwable {
        DS ds = (DS) joinPoint.getSignature().getDeclaringType().getAnnotation(DS.class);
        try {
            DSTypeContainer.setDataSourceType(ds == null ? DS.DSType.write : ds.value());
            return joinPoint.proceed();
        } finally {
            DSTypeContainer.clearDataSourceType();
        }
    }

    // @Before("execution(* org.luvx.boot.mul.mybatis.mapper.UpdateMapper.*(..))")
    // public void setDataSource1() {
    //     DSTypeContainer.setDataSourceType(DS.DSType.write);
    // }
    //
    // @Before("execution(* org.luvx.boot.mul.mybatis.mapper.SelectMapper.*(..))")
    // public void setDataSource2() {
    //     DSTypeContainer.setDataSourceType(DS.DSType.read);
    // }
}