package org.luvx.dynamic.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;

/**
 * @ClassName: org.luvx.config
 * @Description:经过验证 动态任务时,
 * 此类可以有多个, 也可以在同一个类中多次执行addTriggerTask()方法
 * @Author: Ren, Xie
 */
@Slf4j
// @Configuration
public class DynamicTask implements SchedulingConfigurer {
    private int    cnt  = 5;
    private String cron = "0/1 * * * * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //<editor-fold desc="动态定时任务">
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> {
                    log.info("动态定时任务: {}", LocalDateTime.now());
                    cnt--;
                    if (cnt == 0) {
                        String before = cron;
                        cron = "0/5 * * * * ?";
                        log.info("任务配置 {} -> {}", before, cron);
                    }
                },
                //2.设置执行周期(Trigger)
                triggerContext -> new CronTrigger(cron).nextExecutionTime(triggerContext)
        );
        //</editor-fold>
    }
}