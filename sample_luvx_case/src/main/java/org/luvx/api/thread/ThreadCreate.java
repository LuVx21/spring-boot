package org.luvx.api.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程的实现方式理解与不同
 */
public class ThreadCreate {
    // 方式1
    class ThreadCase extends Thread {
        @Override
        public void run() {
            System.out.println("继承Thread!");
        }
    }

    // 方式2
    class ThreadCase1 implements Runnable {
        @Override
        public void run() {
            System.out.println("实现Runnable!");
        }
    }

    // 方式3
    class ThreadCase2 implements Callable<String> {
        @Override
        public String call() {
            System.out.println("实现Callable!");
            return "end";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadCreate threadCreate = new ThreadCreate();

        ThreadCase thread0 = threadCreate.new ThreadCase();
        thread0.start();

        ThreadCase1 threadCase = threadCreate.new ThreadCase1();
        Thread thread1 = new Thread(threadCase, "thread1");
        thread1.start();

        ThreadCase2 thread3 = threadCreate.new ThreadCase2();
        FutureTask<String> task = new FutureTask<>(thread3);
        new Thread(task).start();
        String result = task.get();
        System.out.println(result);
    }
}
