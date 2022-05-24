package org.luvx.boot.mul.mybatis.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Aspect
@Component
public class DsAspect {

    @Around("@within(DS)")
    public Object dsAround(ProceedingJoinPoint joinPoint) throws Throwable {
        DS ds = (DS) joinPoint.getSignature().getDeclaringType().getAnnotation(DS.class);
        try {
            DSTypeContainer.setDataBaseType(ds == null ? "write" : ds.value());
            return joinPoint.proceed();
        } finally {
            DSTypeContainer.clearDataBaseType();
        }
    }
}