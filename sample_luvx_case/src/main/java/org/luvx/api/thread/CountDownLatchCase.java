package org.luvx.api.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 阻塞主进程
 * 区别于CyclicBarrier的阻塞子进程
 *
 * @author renxie
 */
public class CountDownLatchCase {

    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(3000));
                    System.out.println(Thread.currentThread().getName() + "-" + finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 每个线程结束后执行
                countDownLatch.countDown();
            }).start();
        }
        // 所有线程结束后,倒计时完成开始执行
        countDownLatch.await();
        System.out.println("end");
    }

}
