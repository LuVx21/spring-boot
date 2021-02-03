package org.luvx.schedule.dynamic.pojo;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Ren, Xie
 */
@Slf4j
public class TaskCallable implements
        Runnable
        // Callable<Long>
{

    private String sql;

    public TaskCallable(String sql) {
        this.sql = sql;
    }

    // @Override
    // public Long call() {
    //     return ThreadLocalRandom.current().nextLong() % 2;
    // }

    @Override
    public void run() {
        log.info("现在:{}", LocalDateTime.now());
    }
}
