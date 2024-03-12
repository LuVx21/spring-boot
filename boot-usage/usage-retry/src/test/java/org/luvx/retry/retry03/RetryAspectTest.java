package org.luvx.retry.retry03;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.retry.common.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Ren, Xie
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RetryAspectTest {
    @Autowired
    Task task;

    @Test
    public void a() {
        task.accept(0);
    }
}