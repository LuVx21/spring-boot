package org.luvx.schedule.v2.config;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Ren, Xie
 */
@Configuration
public class AppConfig {

    @Bean
    @ConditionalOnMissingBean
    public CronParser cronParser() {
        CronDefinition cronDefinition =
                CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING);
        return new CronParser(cronDefinition);
    }

    @Bean
    public ScheduledThreadPoolExecutor setExecutor() {
        return new ScheduledThreadPoolExecutor(20, new ThreadFactoryBuilder()
                .setNameFormat("schedule-executor-%d")
                .build());
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("scheduler-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
