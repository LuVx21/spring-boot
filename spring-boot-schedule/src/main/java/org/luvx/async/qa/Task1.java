package org.luvx.async.qa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author Ren, Xie
 */
@Slf4j
@Component
public class Task1 {
    /**
     * 每分钟的0秒开始,每10s触发一次, 每次执行9s
     *
     * @throws Exception
     */
    @Async
    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() throws Exception {
        log.info("线程: {} task1 start......: {}", Thread.currentThread().getName(), LocalDateTime.now());
        TimeUnit.SECONDS.sleep(9);
    }

    /**
     * 每分钟的0秒开始,每5s触发一次
     */
    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void execute1() {
        log.info("线程: {} task2 start......: {}", Thread.currentThread().getName(), LocalDateTime.now());
    }
}