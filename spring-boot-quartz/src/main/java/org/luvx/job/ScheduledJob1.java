package org.luvx.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx.task
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 11:06
 */
@Slf4j
@Component
public class ScheduledJob1 extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("执行自定义定时任务: {}", LocalDateTime.now());
    }
}
