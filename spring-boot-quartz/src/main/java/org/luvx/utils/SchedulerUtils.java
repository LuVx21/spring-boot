package org.luvx.utils;

import lombok.extern.slf4j.Slf4j;
import org.luvx.job.BaseJob;
import org.luvx.listener.SchedulerListener;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.RuntimeUtil;

/**
 * 此处可以注入数据库操作，查询出所有的任务配置
 */
@Slf4j
@Component
public class SchedulerUtils {
    @Autowired
    private JobListener          scheduleListener;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

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
            scheduleListener = new SchedulerListener();
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

    /**
     * 创建触发器工厂类
     *
     * @param jobDetail
     * @return
     */
    public static CronTriggerFactoryBean createCronTriggerFactory(JobDetail jobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setStartDelay(1000L);
        trigger.setName("trigger1");
        trigger.setGroup("group1");
        trigger.setCronExpression("0/5 * * * * ?");
        return trigger;
    }

    /**
     * 创建触发器
     *
     * @param factory
     * @return
     */
    public static CronTrigger createCronTrigger(CronTriggerFactoryBean factory) {
        CronTrigger trigger = factory.getObject();
        return trigger;
    }

    /**
     * 创建JobDetail工厂
     *
     * @param job
     * @return
     */
    public static MethodInvokingJobDetailFactoryBean createJobDetailFactory(BaseJob job) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setName("job-group-foo");
        jobDetail.setGroup("group-foo");
        jobDetail.setTargetObject(job);
        jobDetail.setTargetMethod(BaseJob.METHOD_NAME);
        return jobDetail;
    }

    /**
     * 创建JobDetail
     *
     * @param factory
     * @return
     */
    public static JobDetail createJobDetail(MethodInvokingJobDetailFactoryBean factory) {
        JobDetail jobDetail = factory.getObject();
        return jobDetail;
    }

    /**
     * 创建调度器工厂
     *
     * @param cronJobTrigger
     * @return
     */
    public static SchedulerFactoryBean createSchedulerFactory(CronTriggerFactoryBean cronJobTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        bean.setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        CronTrigger trigger = cronJobTrigger.getObject();
        bean.setTriggers(trigger);
        return bean;
    }

    /**
     * 创建调度器
     *
     * @param factory
     * @return
     */
    public static Scheduler createScheduler(SchedulerFactoryBean factory) {
        Scheduler scheduler = factory.getScheduler();
        return scheduler;
    }
}