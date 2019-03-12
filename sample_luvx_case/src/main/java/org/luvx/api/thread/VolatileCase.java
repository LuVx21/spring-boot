package org.luvx.api.thread;

/**
 * volatile 未能保证原子性
 */
public class VolatileCase {
    public volatile int inc = 0;

    public void increase() {
        // 自增操作不保证原子性
        inc++;
    }

    // 实现原子性方案1:同步
    /*
    public int inc = 0;

    public synchronized void increase() {
        inc++;
    }
    */

    // 实现原子性方案2:加锁
    /*
    public int inc = 0;
    Lock lock = new ReentrantLock();

    public void increase() {
        lock.lock();
        try {
            inc++;
        } finally {
            lock.unlock();
        }
    }
    */

    // 实现原子性方案3:元组操作
    /*
    public AtomicInteger inc = new AtomicInteger();

    public void increase() {
        inc.getAndIncrement();
    }
    */

    public static void main(String[] args) {
        final VolatileCase test = new VolatileCase();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++)
                        test.increase();
                }
            }.start();
        }

        while (Thread.activeCount() > 1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
    }

}
