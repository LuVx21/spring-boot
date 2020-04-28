package org.luvx.utils;

import lombok.extern.slf4j.Slf4j;
import org.luvx.config.ApplicationContextUtils;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 *
 */
@Slf4j
public class SchedulerUtils {
    private static JobListener scheduleListener;

    /**
     * 开始定时任务
     *
     * @param cron
     * @param jobName
     * @param jobGroup
     * @param jobClass
     * @throws SchedulerException
     */
    public static void startJob(String cron, String jobName, String jobGroup, Class<? extends Job> jobClass)
            throws SchedulerException {
        Scheduler scheduler = getScheduler();
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
     * 更新 cron
     *
     * @param jobName
     * @param jobGroup
     */
    public static void updateJobCron(String jobName, String jobGroup, String cron) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 删除定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public static void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = getScheduler();
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
    public static void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 触发任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public static void triggerJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.triggerJob(jobKey);
    }

    /**
     * 恢复定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public static void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        JobKey triggerKey = new JobKey(jobName, jobGroup);
        scheduler.resumeJob(triggerKey);
    }

    /**
     * 清空所有当前scheduler对象下的定时任务【目前只有全局一个scheduler对象】
     *
     * @throws SchedulerException
     */
    public static void clearAll() throws SchedulerException {
        Scheduler scheduler = getScheduler();
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
    private static void scheduleJob(String cron, Scheduler scheduler,
                                    String jobName, String jobGroup,
                                    Class<? extends Job> jobClass)
            throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    private static Scheduler getScheduler() {
        SchedulerFactoryBean scheduler = ApplicationContextUtils.getBean(SchedulerFactoryBean.class);
        return scheduler.getScheduler();
    }
}