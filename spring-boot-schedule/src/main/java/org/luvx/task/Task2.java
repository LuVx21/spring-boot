package org.luvx.task;

import org.luvx.utils.DateTimeUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: org.luvx.task
 * @Description: 测试任务累积
 */
@Component
public class Task2 {
    private static int i = 1;

    // @Async 加了异步, 任务不会累计, 按时启动的任务以多个线程执行
    // @Scheduled(fixedRate = 3 * 1000)
    public void method() throws Exception {
        System.out.println("time: " + DateTimeUtils.formatter1.format(System.currentTimeMillis()));
        if (i == 1) {
            Thread.sleep(21 * 1000);
            i = 0;
        }
        method1();
    }

    private static int aa = 1;

    public void method1() {
        aa++;
        System.out.println(Thread.currentThread().getName() + ": " + aa);
    }
}