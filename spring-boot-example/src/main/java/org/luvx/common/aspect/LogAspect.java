package org.luvx.common.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.luvx.common.annotation.LogAnnotation;
import org.luvx.common.utils.HttpContextUtils;
import org.luvx.module.log.entity.LogPO;
import org.luvx.module.log.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: org.luvx.common.aspect
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 16:52
 */
@Slf4j
@Aspect
@Component
@Profile({"dev", "test"})
public class LogAspect {

    @Autowired
    private LogMapper logMapper;

    @Pointcut("@annotation(org.luvx.common.annotation.LogAnnotation)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object result = joinPoint.proceed();
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        saveRequest(joinPoint);
        saveResponse(result);
        return result;
    }

    /**
     * 记录请求信息
     *
     * @param joinPoint
     */
    private void saveRequest(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("非方法注解");
        }
        methodSignature = (MethodSignature) signature;
        LogPO.LogPOBuilder builder = LogPO.builder();
        Method method = methodSignature.getMethod();

        LogAnnotation log = method.getAnnotation(LogAnnotation.class);
        if (log != null) {
            builder.operation(log.value());
        }

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request != null) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, Object> map = new HashMap<>(parameterMap.size() + 1);
            map.putAll(parameterMap);
            map.put("body", joinPoint.getArgs());

            String param = JSON.toJSONString(map);
            builder.method(request.getMethod() + " " + request.getRequestURI())
                    .params(param);
        } else {
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = methodSignature.getName();
            String param = JSON.toJSONString(joinPoint.getArgs());
            builder.method(className + "." + methodName + "()")
                    .params(param);
        }

        builder.createTime(LocalDateTime.now());
        LogPO po = builder.build();

        // logMapper.insert(po);
    }

    /**
     * 记录响应信息
     */
    private void saveResponse(Object result) {
        log.info("返回值: {}", String.valueOf(result));
    }
}
