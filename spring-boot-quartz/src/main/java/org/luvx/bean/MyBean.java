package org.luvx.bean;

import org.quartz.JobDataMap;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
// import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean

/**
 * @ClassName: org.luvx
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 10:30
 */
public class MyBean  {

    // @Bean("methodInvokingJobDetailFactoryBean")
    // public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean(){
    //     MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
    //     // bean.setTargetObject(jobBean);
    //     // bean.setTargetMethod("printMessage");
    //     // bean.setConcurrent(false);
    //     return bean;
    // }


    // public JobDetailFactoryBean jobDetailFactoryBean(){
    //     JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
    //     jobDetailFactoryBean.setJobClass(scheduledJob.getClass());
    //     JobDataMap jobDataMap = new JobDataMap();
    //     jobDataMap.put("jobCornBean",jobCornBean);  //额外参数，对应上文的anotherbean
    //     jobDetailFactoryBean.setJobDataMap(jobDataMap);
    //     jobDetailFactoryBean.setDurability(true);
    //     return jobDetailFactoryBean;
    // }

}
