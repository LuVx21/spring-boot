package org.luvx.api.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 使用AQS实现锁
 * 实现Lock接口
 * 内部类Sync继承AbstractQueuedSynchronizer
 */
public class SimplifyReentrantLock implements Lock {

    private final Sync sync = new Sync();

    /**
     * AQS的子类Sync
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean isHeldExclusively() {
            //是否处于占用状态
            return getState() == 1;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            //当状态为0是获取锁
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            //释放锁，将状态设置为0
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        Condition newCondition() {
            return new ConditionObject();
        }

    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public static void main(String[] args) {
        SimplifyReentrantLock lock = new SimplifyReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("进入等待!");
                condition.await();
                System.out.println("接收到通知!继续执行!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }, "conditionAwaitThread").start();

        new Thread(() -> {
            try {
                System.out.println("模拟3秒后唤醒!");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            System.out.println("唤醒!");
            condition.signal();
            lock.unlock();
        }, "conditionSignalThread").start();
    }
}