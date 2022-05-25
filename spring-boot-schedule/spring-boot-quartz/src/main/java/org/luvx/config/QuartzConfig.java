package org.luvx.config;

import lombok.Data;
import org.luvx.job.BaseJob;
import org.luvx.job.custom.CustomJob1;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: org.luvx.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 10:02
 */
// @Configuration
public class QuartzConfig {
    @Bean("methodInvokingJobDetailFactoryBean")
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean factory = new MethodInvokingJobDetailFactoryBean();
        factory.setTargetObject(new CustomJob1());
        factory.setTargetMethod("executeNoArgs");
        factory.setConcurrent(false);
        return factory;
    }

    @Bean("jobDetailFactoryBean")
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(BaseJob.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("customJob", new CustomJob1());
        factory.setJobDataMap(jobDataMap);
        factory.setDurability(true);
        return factory;
    }

    @Bean("simpleTriggerFactoryBean")
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(MethodInvokingJobDetailFactoryBean jobDetailFactory) {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setJobDetail(jobDetailFactory.getObject());
        factory.setStartDelay(1_000);
        factory.setRepeatInterval(4_000);
        return factory;
    }

    @Bean("cronTriggerFactoryBean")
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetailFactoryBean.getObject());
        factory.setCronExpression("0/5 * * * * ?");
        return factory;
    }

    @Bean("schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean(JobConfigBean bean) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobDetails(bean.getJobDetails());
        factory.setTriggers(bean.getTriggers());
        return factory;
    }

    @Bean("jobConfigBean")
    public JobConfigBean jobConfigBean() {
        JobConfigBean bean = new JobConfigBean();

        List<JobDetail> jobDetails = new ArrayList<>();
        List<Trigger> triggers = new ArrayList<>();

        MethodInvokingJobDetailFactoryBean factory = methodInvokingJobDetailFactoryBean();
        jobDetails.add(factory.getObject());
        triggers.add(simpleTriggerFactoryBean(factory).getObject());

        JobDetailFactoryBean factory1 = jobDetailFactoryBean();
        jobDetails.add(factory1.getObject());
        triggers.add(cronTriggerFactoryBean(factory1).getObject());

        bean.setJobDetails(jobDetails.stream().toArray(JobDetail[]::new));
        bean.setTriggers(triggers.stream().toArray(Trigger[]::new));

        return bean;
    }

    @Data
    public class JobConfigBean {
        private JobDetail[] jobDetails;
        private Trigger[]   triggers;
    }

    @Component("quartzJobFactory")
    public class QuartzJobFactory extends AdaptableJobFactory {
        @Autowired
        private AutowireCapableBeanFactory capableBeanFactory;

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            Object jobInstance = super.createJobInstance(bundle);
            capableBeanFactory.autowireBean(jobInstance);
            return jobInstance;
        }
    }

    @Bean(name = "scheduler")
    public Scheduler scheduler(QuartzJobFactory quartzJobFactory) throws Exception {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setJobFactory(quartzJobFactory);
        factoryBean.afterPropertiesSet();
        Scheduler scheduler = factoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }
}

