package org.luvx.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.luvx.job.custom.CustomJob;
import org.luvx.job.custom.CustomJob1;
import org.luvx.listener.SchedulerListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Arrays;

/**
 * @ClassName: org.luvx.task
 * @Description: 可自定义执行内容的job
 * @Author: Ren, Xie
 * @Date: 2019/3/7 11:06
 * <p>
 * 可以在运行时选择不同的自定义内容job赋值给customJob达到切换不同执行策略的目的
 */
@Slf4j
public class BaseJob
        // implements Job {
        extends QuartzJobBean {
    @Setter
    private CustomJob customJob;

    // @Override
    // public void execute(JobExecutionContext context) throws JobExecutionException {
    //     if (customJob != null) {
    //         customJob.execute(context);
    //     }
    // }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        if (customJob != null) {
            customJob.execute(context);
        }
    }

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(BaseJob.class)
                .withIdentity(JobKey.jobKey("job1", "group1"))
                .build();
        JobDataMap map = jobDetail.getJobDataMap();
        map.put("description", "Hello Quartz");
        map.put("myValue", 1990);
        map.put("myArray", Arrays.asList("firstItem"));
        map.put("customJob", new CustomJob1());

        // Trigger trigger1 = TriggerBuilder.newTrigger()
        //         .withIdentity("trigger1", "group1")
        //         .startAt(DateBuilder.evenSecondDate(new Date()))
        //         .withSchedule(SimpleScheduleBuilder.simpleSchedule()
        //                 .withIntervalInSeconds(5)
        //                 .withRepeatCount(2))
        //         .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();

        scheduler.getListenerManager()
                .addJobListener(
                        new SchedulerListener(),
                        KeyMatcher.keyEquals(JobKey.jobKey("job1", "group1"))
                );

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        // scheduler.shutdown(true);
    }
}
