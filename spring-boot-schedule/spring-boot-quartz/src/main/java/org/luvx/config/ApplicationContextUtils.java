package org.luvx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName: org.luvx.common.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/28 14:17
 */
@Component
@Slf4j
public class ApplicationContextUtils implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    @Override
    public void destroy() {
        if (log.isDebugEnabled()) {
            log.info("销毁applicationContext: " + applicationContext);
        }
        applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        hasInjected();
        return applicationContext;
    }

    /**
     * check是否已注入
     */
    private static void hasInjected() {
        Objects.requireNonNull(applicationContext, "尚未注入, 请确认配置!");
    }

    /**
     * 以名称获取bean
     *
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        hasInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 以类型获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        hasInjected();
        return applicationContext.getBean(clazz);
    }
}
