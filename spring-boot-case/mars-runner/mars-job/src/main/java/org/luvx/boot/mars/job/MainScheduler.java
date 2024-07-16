package org.luvx.boot.mars.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mars.service.retrofit.WeiboService;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import jakarta.annotation.Resource;

@Slf4j
// @Component
public class MainScheduler implements SchedulingConfigurer {
    @Setter
    private String cron  = "0 3/10 9-23 * * ?";
    @Setter
    private Long   timer = 10_000L;

    @Resource
    private WeiboService weiboService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                () -> weiboService.pullByGroup(),
                tc -> {
                    Trigger Trigger = new CronTrigger(cron);
                    // Trigger Trigger = new PeriodicTrigger(timer, TimeUnit.MILLISECONDS);
                    return Trigger.nextExecution(tc);
                }
        );
    }
}
