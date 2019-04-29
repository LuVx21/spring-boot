package org.luvx.api.thread.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @ClassName: org.luvx.api.thread.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 15:08
 */
public class ThreadUtils {

    private static final int size = 5;

    /**
     * @return
     */
    public static ExecutorService getThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

        ///
        // ThreadFactory factory = (Runnable r) -> {
        //     Thread thread = new Thread(r);
        //     thread.setName(r.getClass().getName());
        //     return thread;
        // };

        ExecutorService service = new ThreadPoolExecutor(size,
                size << 1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        return service;
    }
}
