package org.luvx.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.common.aspect
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 16:52
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(org.luvx.common.annotation.LogAnnotation)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
    }
}
