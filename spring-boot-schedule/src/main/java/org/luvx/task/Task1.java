package org.luvx.task;

import org.luvx.utils.DateTimeUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.task
 * @Description: 解决任务互相影响(并发执行定时任务)
 */
@Component
public class Task1 {
    // @Async
    // @Scheduled(cron = "0 * * * * ?")
    public void execute() throws Exception {
        System.out.println("线程: " + Thread.currentThread().getName() + " task1 running......: " + DateTimeUtils.formatter1.format(System.currentTimeMillis()));
        int i = 0;
        while (i < 10) {
            Thread.sleep(1 * 1000);
            System.out.println("线程: " + Thread.currentThread().getName() + " task1 running......: " + DateTimeUtils.formatter1.format(System.currentTimeMillis()));
            i++;
        }
        System.out.println("线程: " + Thread.currentThread().getName() + " task1 over......: " + DateTimeUtils.formatter1.format(System.currentTimeMillis()));
    }

    // @Async
    // @Scheduled(cron = "5 * * * * ?")
    public void execute1() {
        System.out.println("线程: " + Thread.currentThread().getName() + " task2 running......: " + DateTimeUtils.formatter1.format(System.currentTimeMillis()));
    }
}