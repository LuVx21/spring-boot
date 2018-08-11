package org.luvx.api.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Function: 两个线程交替执行打印 1~100 lock 版
 * 取材于https://github.com/crossoverJie/ 为方便阅读略有修改
 */
public class TwoNumLock {
    public static void main(String[] args) {
        TwoNumLock twoNum = new TwoNumLock();

        Thread t1 = new Thread(new JiNum(twoNum), "t1");
        Thread t2 = new Thread(new OuNum(twoNum), "t2");

        t1.start();
        t2.start();
    }

    private int num = 1;

    // 输入奇数还是偶数的控制flag
    private volatile boolean flag = false;

    private final static Lock LOCK = new ReentrantLock();

    public static class OuNum implements Runnable {
        private TwoNumLock twoNum1;

        public OuNum(TwoNumLock twoNum) {
            this.twoNum1 = twoNum;
        }

        @Override
        public void run() {
            while (twoNum1.num <= 100) {

                if (twoNum1.flag) {
                    try {
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName() + "+-+" + twoNum1.num);
                        twoNum1.num++;
                        twoNum1.flag = false;
                    } finally {
                        LOCK.unlock();
                    }
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class JiNum implements Runnable {
        private TwoNumLock twoNum2;

        public JiNum(TwoNumLock twoNum) {
            this.twoNum2 = twoNum;
        }

        @Override
        public void run() {
            while (twoNum2.num <= 100) {

                if (!twoNum2.flag) {
                    try {
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName() + "+-+" + twoNum2.num);
                        twoNum2.num++;
                        twoNum2.flag = true;
                    } finally {
                        LOCK.unlock();
                    }
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
