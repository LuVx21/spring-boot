package org.luvx.schedule.qa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 验证:
 * 任务互相影响(并发执行定时任务)
 * 具体现象:
 * ScheduledConfig类中不配置线程数量, 则默认是1
 * 那么可能出现task2即使到了指定的时间点, 也不会被触发, 必须在task1执行完一次后才能执行
 * 即task1阻碍了task2的执行
 * 解决方案:
 * 1. 增加线程池线程数量: ScheduledConfig中增加线程数量(任务多于线程数的场景下仍可能出现)
 * 2. 异步方式: 使用 {@code @Async} 注解
 * <p>
 * 以下代码task1可能会影响task2
 * * 0s时的执行
 * * 5s时的执行, 导致task2的第5s总是在第9s执行
 *
 * @author Ren, Xie
 */
@Slf4j
// @Component
public class Task1 {
    /**
     * 每分钟的0秒开始,每10s触发一次, 每次执行10s
     *
     * @throws Exception
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() throws Exception {
        log.info("线程: {} task1 start......: {}", Thread.currentThread().getName(), LocalDateTime.now());
        TimeUnit.SECONDS.sleep(9);
    }

    /**
     * 每分钟的0秒开始,每5s触发一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void execute1() {
        log.info("线程: {} task2 start......: {}", Thread.currentThread().getName(), LocalDateTime.now());
    }
}