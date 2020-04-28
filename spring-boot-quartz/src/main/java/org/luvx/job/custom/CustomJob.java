package org.luvx.job.custom;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.task
 * @Description: 不继承类, 不实现接口自定义job
 * @Author: Ren, Xie
 * @Date: 2019/3/7 11:06
 */
@Slf4j
public abstract class CustomJob {
    /**
     * jod执行入口
     */
    public abstract void executeNoArgs();

    /**
     * jod执行入口
     *
     * @param context
     */
    public abstract void execute(JobExecutionContext context);
}
