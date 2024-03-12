package org.luvx.retry.retry03;

import org.junit.jupiter.api.Test;
import org.luvx.retry.RetryAppTest;
import org.luvx.boot.retry.common.Task;
import org.springframework.beans.factory.annotation.Autowired;

public class RetryAspectTest extends RetryAppTest {
    @Autowired
    Task task;

    @Test
    public void a() {
        task.accept(0);
    }
}