package org.luvx.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

/**
 * @ClassName: org.luvx.async
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/21 19:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncTaskTest {

    @Autowired
    private AsyncTask asyncTask;

    /**
     * 异步调用
     *
     * @throws Exception
     */
    @Test
    public void method() throws Exception {
        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
    }

    /**
     * 异步回调
     *
     * @throws Exception
     */
    @Test
    public void method1() throws Exception {
        long start = System.currentTimeMillis();

        Future<String> task1 = asyncTask.doTaskOne();
        Future<String> task2 = asyncTask.doTaskTwo();
        Future<String> task3 = asyncTask.doTaskThree();

        while (true) {
            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                break;
            }
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}