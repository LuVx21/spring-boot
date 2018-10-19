package org.luvx.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MeasurementAspect {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementAspect.class);

    @Pointcut("@annotation(org.luvx.annotation.MeasurementAnno)")
    public void annotationPoinCut() {
    }

    @Around("annotationPoinCut()")
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
        ;
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
            logger.info(buf.toString() + " 执行了" + (end - start) + "ms");
        }
    }

}