package org.luvx.job;

import lombok.extern.slf4j.Slf4j;
import org.luvx.listener.SchedulerListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: org.luvx.task
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 11:06
 */
@Slf4j
public class ScheduledJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        log.info("执行自定义定时任务: {}", LocalDateTime.now());
    }
}
