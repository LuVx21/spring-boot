package org.luvx.utils;

import lombok.extern.slf4j.Slf4j;
import org.luvx.config.ApplicationContextUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@Component
public class SchedulerUtils {
    private static JobListener          scheduleListener;
    private static SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    public void setScheduleListener(JobListener listener) {
        SchedulerUtils.scheduleListener = listener;
    }

    @Autowired
    public void setSchedulerFactoryBean(SchedulerFactoryBean bean) {
        SchedulerUtils.schedulerFactoryBean = bean;
    }

    /**
     * 开始定时任务
     *
     * @param cron
     * @param jobName
     * @param jobGroup
     * @param jobClass
     * @throws SchedulerException
     */
    public void startJob(String cron, String jobName, String jobGroup, Class<? extends Job> jobClass)
            throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (scheduleListener == null) {
            scheduleListener = ApplicationContextUtils.getBean("schedulerListener");
        }
        scheduler.getListenerManager().addJobListener(scheduleListener);
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (!scheduler.checkExists(jobKey)) {
            scheduleJob(cron, scheduler, jobName, jobGroup, jobClass);
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
    }

    /**
     * 暂停定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey triggerKey = new JobKey(jobName, jobGroup);
        scheduler.resumeJob(triggerKey);
    }

    /**
     * 清空所有当前scheduler对象下的定时任务【目前只有全局一个scheduler对象】
     *
     * @throws SchedulerException
     */
    public void clearAll() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.clear();
    }

    /**
     * 动态创建Job
     *
     * @param cron
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @param jobClass
     * @throws SchedulerException
     */
    private void scheduleJob(String cron, Scheduler scheduler,
                             String jobName, String jobGroup, Class<? extends Job> jobClass)
            throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(scheduleBuilder)
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}