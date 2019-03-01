package org.luvx.api.thread;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName: org.luvx.api.thread
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/2/28 10:59
 */
public class ExecutorServiceCase {
    public static void main(String[] args) {
        List<String> list = ImmutableList.of("begin", "code", "Guava", "Java");
        run01(list);
    }

    public static void run01(List<String> list) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService service = new ThreadPoolExecutor(3,
                3,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            service.execute(() -> {
                method(name);
            });
        }
        service.shutdown();
    }

    public static void method(String name) {
        try {
            System.out.println("正在处理: " + name);
            Thread.sleep(5 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
