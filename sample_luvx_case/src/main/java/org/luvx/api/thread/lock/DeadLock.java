package org.luvx.api.thread.lock;

/**
 * 死锁实现例1
 */
public class DeadLock {
    public static Object objA = new Object();
    public static Object objB = new Object();

    public static void getLockA() {
        synchronized (objA) {
            getLockB();
        }
    }

    public static void getLockB() {
        synchronized (objB) {
            getLockA();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            DeadLock.getLockA();
        }).start();

        new Thread(() -> {
            DeadLock.getLockB();
        }).start();
    }
}
