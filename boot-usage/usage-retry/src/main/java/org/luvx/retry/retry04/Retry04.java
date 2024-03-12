package org.luvx.retry.retry04;

import lombok.extern.slf4j.Slf4j;
import org.luvx.retry.common.Task;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author: Ren, Xie
 * @desc: 使用spring-retry的实现
 */
@Slf4j
@Service
public class Retry04 {
    static int no = 0;

    @Retryable(value = Exception.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 1_000L, multiplier = 2))
    public void call() {
        log.info("第{}次执行({})", ++no, LocalDateTime.now());
        new Task().accept(0);
    }

    @Recover
    public void recover(RuntimeException e) {
        log.error("超出重试次数", e);
    }
}
