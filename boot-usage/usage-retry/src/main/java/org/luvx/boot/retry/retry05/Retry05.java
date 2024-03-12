package org.luvx.boot.retry.retry05;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.retry.common.Task;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Retry05 {
    static Callable<Boolean> callable = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            new Task().accept(0);
            return false;
        }
    };

    public static void main(String[] args) {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.isNull())
                .retryIfExceptionOfType(Exception.class)
                .retryIfRuntimeException()
                // 等待1秒后重试
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                // 5次后停止重试
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withRetryListener(
                        new RetryListener() {
                            @Override
                            public <T> void onRetry(Attempt<T> attempt) {
                                log.info("第{}次执行({})", attempt.getAttemptNumber(), LocalDateTime.now());
                            }
                        }
                )
                .build();
        try {
            retryer.call(callable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
