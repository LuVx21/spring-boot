package org.luvx.utils;

import org.luvx.job.custom.CustomJob1;
import org.quartz.*;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class SchedulerBeanUtils {
    /**
     * 创建JobDetail工厂
     *
     * @param job
     * @return
     */
    public static MethodInvokingJobDetailFactoryBean createJobDetailFactory(CustomJob1 job) {
        MethodInvokingJobDetailFactoryBean factory = new MethodInvokingJobDetailFactoryBean();
        factory.setConcurrent(false);
        factory.setName("job-group-foo");
        factory.setGroup("group-foo");
        factory.setTargetObject(job);
        factory.setTargetMethod("execute");
        return factory;
    }

    public JobDetailFactoryBean jobDetailFactoryBean(Job job) {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(job.getClass());
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    /**
     * 创建触发器工厂类
     *
     * @param jobDetail
     * @return
     */
    public static CronTriggerFactoryBean createCronTriggerFactory(JobDetail jobDetail) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setStartDelay(1_000L);
        factory.setName("job-group-foo1");
        factory.setGroup("group-foo1");
        factory.setCronExpression("0/5 * * * * ?");
        return factory;
    }

    /**
     * 创建调度器工厂
     *
     * @param trigger
     * @return
     */
    public static SchedulerFactoryBean createSchedulerFactory(CronTrigger trigger) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName("main-scheduler");
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        factory.setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
        factory.setStartupDelay(1);
        // 注册触发器
        factory.setTriggers(trigger);
        return factory;
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
