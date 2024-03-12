package org.luvx.boot.retry.retry01;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.retry.common.RetryConstant;
import org.luvx.boot.retry.common.Task;

import java.time.LocalDateTime;

/**
 * 简单的重试方式, 遇到异常等待一定的时间后重试
 */
@Slf4j
public class Retry01 {
    static int no = 0;

    public static void retry() {
        while (no < RetryConstant.MAX_TIMES) {
            try {
                new Task().accept(no);
            } catch (Exception e) {
                no++;
                log.info("第{}次执行({})", no, LocalDateTime.now());
                try {
                    Thread.sleep(no * 1_000);
                } catch (InterruptedException ee) {
                }
                if (no >= RetryConstant.MAX_TIMES) {
                    log.error("超出重试次数", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        Retry01.retry();
    }
}
