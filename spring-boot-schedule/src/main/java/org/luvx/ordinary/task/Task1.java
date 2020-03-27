package org.luvx.ordinary.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName: org.luvx.task
 * @Description: 验证:
 * 任务互相影响(并发执行定时任务)
 * 具体现象:
 * ScheduledConfig类中不配置线程数量, 则默认是1
 * 那么可能出现task2即使到了指定的时间点, 也不会被触发, 必须在task1执行完一次后才能执行
 * 即task1阻碍了task2的执行
 * 解决方案:
 * 1. 增加线程池线程数量: ScheduledConfig中增加线程数量
 * 2. 异步方式: 使用 @Async 注解 -> 将下面的注解放开即可
 */
@Slf4j
// @Component
public class Task1 {
    /**
     * 每分钟的0秒开始,每10s触发一次, 每次执行10s
     *
     * @throws Exception
     */
    /// @Async
    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() throws Exception {
        log.info("线程: {} task1 start......: {}", Thread.currentThread().getName(), LocalDateTime.now());
        Thread.sleep(10_000);
        log.info("线程: {} task1 end......: {}", Thread.currentThread().getName(), LocalDateTime.now());
    }

    /**
     * 每分钟的0秒开始,每5s触发一次
     */
    /// @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void execute1() {
        log.info("线程: {} task2 start......: {}", Thread.currentThread().getName(), LocalDateTime.now());
        log.info("线程: {} task2 end......: {}", Thread.currentThread().getName(), LocalDateTime.now());
    }
}