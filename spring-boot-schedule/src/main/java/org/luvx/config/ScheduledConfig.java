package org.luvx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @ClassName: org.luvx.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 13:50
 */
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    private final int size = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(size));
    }
}