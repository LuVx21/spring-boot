package org.luvx.api.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal:线程局部变量
 * 提供线程内的局部变量，这种变量在线程的生命周期内起作用，减少同一个线程内多个函数或者组件之间一些公共变量的传递的复杂度
 * Thread ThreadLocalMap ThreadLocal
 */
public class ThreadLocalCase {

    private static ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocal2 = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocal3 = new ThreadLocal<>();


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(() ->

        {
            threadLocal1.set("123");
            threadLocal2.set("234");
            threadLocal3.set("345");
            System.out.println(threadLocal1.get());
            System.out.println(Thread.currentThread().getName());
        });
        executorService.shutdown();
    }

}
