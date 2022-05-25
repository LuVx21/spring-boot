package org.luvx.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class SchedulerListener implements JobListener {

    private static final ThreadLocal<LocalDateTime> time = new ThreadLocal();

    public static final String LISTENER_NAME = "QuartSchedulerListener";

    @Override
    public String getName() {
        return LISTENER_NAME;
    }

    /**
     * 任务调度前执行
     *
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        time.set(LocalDateTime.now());
        String jobName = context.getJobDetail().getKey().toString();
        log.info("job:{} 开始执行...", jobName);
    }

    /**
     * 任务调度被拒绝时执行
     *
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().toString();
        log.info("job:{} 拒绝执行...", jobName);
    }

    /**
     * 任务调度后执行
     *
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        LocalDateTime start = time.get();
        String jobName = context.getJobDetail().getKey().toString();
        log.info("job:{} 执行结束...{} ~ {}", jobName, start, LocalDateTime.now());
        if (jobException != null) {
            log.error("job:{} 异常结束", jobException);
        }
    }
}
