package org.luvx.retry.common;

import lombok.extern.slf4j.Slf4j;
import org.luvx.retry.retry03.RetryableAnno;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Consumer;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Slf4j
@Component
public class Task implements Consumer<Integer> {
    static Random random = new Random();

    @Override
    @RetryableAnno(maxAttempts = 6, value = RuntimeException.class)
    public void accept(Integer no) {
        throw new RuntimeException("模拟需要重试的异常");
    }
}

