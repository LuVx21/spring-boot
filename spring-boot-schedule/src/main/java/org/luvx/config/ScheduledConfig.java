package org.luvx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @ClassName: org.luvx.config
 * @Description:经过验证
 * @Author: Ren, Xie
 */
@Slf4j
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {
    private final int size = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(size));
    }
}