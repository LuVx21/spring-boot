package org.luvx.boot.mul.mybatis.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
}