package org.luvx.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx.task
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 11:06
 * <p>
 * 1. 实现org.quartz.Job接口
 * 2. 继承org.springframework.scheduling.quartz.QuartzJobBean类
 * @see org.quartz.Job
 * @see org.springframework.scheduling.quartz.QuartzJobBean
 */
@Slf4j
public class ScheduledJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        log.info("执行自定义定时任务: {}", LocalDateTime.now());
    }
}
