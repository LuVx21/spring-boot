package org.luvx.api;

/**
 * 对象锁和类锁
 * <p/>
 * 使用对象锁时(synchronized)
 * &nbsp 多线程不能同时访问同一对象的多个同步域
 * &nbsp 多线程不能同时访问同一对象的同一同步域
 * <p/>
 * 使用类锁 (synchronized static)
 * &nbsp 不能访问同一个类的同一或不同一同步域
 */
public class SynchronizedCase {
    /**
     * 同步
     */
    public synchronized void unStaticFun() {
        int i = 0;
        while (i++ < 5) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
    }

    /**
     * 静态同步
     */
    public static synchronized void staticFun() {
        int i = 0;
        while (i++ < 5) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedCase aCase = new SynchronizedCase();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                aCase.unStaticFun();
            }
        }, "thread1");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedCase.staticFun();
            }
        }, "thread2");
        thread1.start();
        thread2.start();
    }

}
