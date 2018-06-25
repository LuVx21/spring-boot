package org.luvx.api.thread;

import java.util.concurrent.*;

/**
 * 线程池
 * ThreadPoolExecutor
 *
 * execute()方法用于提交不需要返回值的任务,无法判断任务是否被线程池成功执行
 * submit()方法用于提交需要返回值的任务
 */
public class ThreadPoolCase {

    private static void divTask(int a, int b) {
        double result = a / b;
        System.out.println(result);
    }

    public static void main(String[] args) {
        // 不推荐
        // ExecutorService executorService = Executors.newFixedThreadPool(3);

        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName(r.getClass().getName());
                        return thread;
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {
            int index = i;
            // submit()方法会屏蔽所有错误信息
            executorService.submit(() -> divTask(100, index));
            // submit替代方案1
            // executorService.execute(() -> divTask(100, index));
            // submit替代方案2
            // Future future = executorService.submit(() -> divTask(200, index));
            // try {
            //     future.get();
            // } catch (InterruptedException | ExecutionException e) {
            //     e.printStackTrace();
            // }
        }

        executorService.shutdown();
    }
}
