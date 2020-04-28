package org.luvx.job.custom;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;

@Slf4j
public class CustomJob1 extends CustomJob {
    @Override
    public void execute(JobExecutionContext context) {
        log.info("执行自定义定时任务: {}", LocalDateTime.now());
    }

    @Override
    public void executeNoArgs() {
        log.info("执行自定义定时任务: {}", LocalDateTime.now());
    }
}
