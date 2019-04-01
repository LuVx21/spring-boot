package org.luvx.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @ClassName: org.luvx.task
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 11:06
 */
public class ScheduledJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("执行自定义定时任务");
    }
}
