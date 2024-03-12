package org.luvx.retry.retry04;

import org.junit.jupiter.api.Test;
import org.luvx.boot.retry.retry04.Retry04;
import org.luvx.retry.RetryAppTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
public class Retry04Test extends RetryAppTest {
    @Autowired
    Retry04 retry04;

    @Test
    public void test() {
        retry04.call();
    }
}