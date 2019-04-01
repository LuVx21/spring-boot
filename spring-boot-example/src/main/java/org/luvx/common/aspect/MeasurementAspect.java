package org.luvx.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.common.aspect
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 16:18
 */
@Slf4j
@Aspect
@Component
@Profile({"dev", "test"})
public class MeasurementAspect {

    @Pointcut("@annotation(org.luvx.common.annotation.MeasurementAnnotation)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        StringBuffer buf = new StringBuffer(100);
        long start = 0;
        long end = 0;
        buf.append("方法执行时间计测:MeasurementAspect  ");
        buf.append(joinPoint.getTarget().getClass().getName());
        buf.append("#");
        buf.append(joinPoint.getSignature().getName());
        buf.append("(");

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; ++i) {
                buf.append(args[i]);
                buf.append(", ");
            }
            buf.setLength(buf.length() - 2);
        }
        buf.append(")");

        try {
            start = System.currentTimeMillis();
            Object obj = joinPoint.proceed();
            end = System.currentTimeMillis();
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        } finally {
            log.info(buf.toString() + " 执行了" + (end - start) + "ms");
        }
    }
}
