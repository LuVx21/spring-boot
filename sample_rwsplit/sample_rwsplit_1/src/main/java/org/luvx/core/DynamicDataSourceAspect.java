package org.luvx.core;

import java.util.List;
import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class DynamicDataSourceAspect {

    @Resource
    private List<String> useSlaveDataSourceMethodPrefix;
    @Resource
    private List<String> useMasterDataSourceMethodPrefix;

    @Before("execution(* org.luvx.dao.*.*(..))")
    public void before(JoinPoint point) {
        String method = point.getSignature().getName();
        boolean useSlave = useSlaveDataSourceMethodPrefix.stream().anyMatch(i -> method.startsWith(i));
        if (useSlave) {
            DynamicDataSourceHolder.setKey(DynamicDataSourceHolder.slave);
            System.out.println("--- slave ---");
            return;
        }
        boolean useMaster = useMasterDataSourceMethodPrefix.stream().anyMatch(i -> method.startsWith(i));
        if (useMaster) {
            DynamicDataSourceHolder.setKey(DynamicDataSourceHolder.master);
            System.out.println("--- master ---");
            return;
        }
        throw new UnsupportedOperationException("can not match datasource for method '" + method + "'");
    }
}