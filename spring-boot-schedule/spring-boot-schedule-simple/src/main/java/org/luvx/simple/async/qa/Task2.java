package org.luvx.simple.async.qa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 测试任务累积
 * <p>
 * 现象:
 * 任务没有执行完就到达下一次的任务启动时间, 这个时间不会触发新的执行
 * 在执行完上一次后将累积任务全部执行了, 这些累积任务没有按照设置的时间正常执行
 * <p>
 * 解决方案:
 * 1. 使用异步(@Async注解)
 *
 * @author Ren, Xie
 */
@Slf4j
@Component
public class Task2 {
    private volatile static boolean b;
    private static          int     cnt = 1;

    /**
     * 每3s执行一次, 第一次执行21s
     * {@code @Async} 加了异步, 任务不会累计, 按时启动的任务以多个线程执行
     *
     * @throws Exception
     */
    @Async
    @Scheduled(fixedRate = 3_000)
    public void method() throws Exception {
        int temp = cnt;
        LocalDateTime start = LocalDateTime.now();
        log.info("start第{}次,线程: {},time: {}", temp,
                Thread.currentThread().getName(),
                start);
        cnt++;
        if (!b) {
            b = true;
            Thread.sleep(21_000);
        }
        log.info("end  第{}次,线程: {},time: {}", temp,
                Thread.currentThread().getName(),
                LocalDateTime.now());
    }
}