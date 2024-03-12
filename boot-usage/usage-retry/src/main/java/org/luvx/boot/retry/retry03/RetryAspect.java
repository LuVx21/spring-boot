package org.luvx.boot.retry.retry03;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 本质仍然是代理模式, 但可以指定重试次数, 比较灵活
 */
@Slf4j
@Aspect
@Component
public class RetryAspect {

    @Pointcut("@annotation(org.luvx.boot.retry.retry03.RetryableAnno)")
    public void myPointcut() {
    }

    @Around("myPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = getCurrentMethod(point);
        RetryableAnno retryableAnno = method.getAnnotation(RetryableAnno.class);

        int maxAttempts = retryableAnno.maxAttempts();
        if (maxAttempts <= 1) {
            return point.proceed();
        }

        int no = 0;
        while (no < maxAttempts) {
            try {
                return point.proceed();
            } catch (Throwable e) {
                no++;
                log.info("第{}次执行({})", no, LocalDateTime.now());
                try {
                    Thread.sleep(no * 1_000);
                } catch (InterruptedException ee) {
                }
                if (no >= maxAttempts || !e.getClass().isAssignableFrom(retryableAnno.value())) {
                    log.error("超出重试次数", e);
                    throw new Throwable(e);
                }
            }
        }

        return null;
    }

    private Method getCurrentMethod(ProceedingJoinPoint point) {
        try {
            MethodSignature sig = (MethodSignature) point.getSignature();
            Object target = point.getTarget();
            return target.getClass().getMethod(sig.getName(), sig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
