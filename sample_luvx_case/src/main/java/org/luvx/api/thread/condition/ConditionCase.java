package org.luvx.api.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁 和 Condition
 * 实现针对唤醒
 */
public class ConditionCase {

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ConditionCase conditionCase = new ConditionCase();
        new Thread(() -> conditionCase.await(conditionCase.condition1), "thread1").start();
        new Thread(() -> conditionCase.await(conditionCase.condition2), "thread2").start();
        new Thread(() -> conditionCase.signal(conditionCase.condition1), "thread3").start();
        System.out.println("------------------");
        Thread.sleep(6000);
        new Thread(() -> conditionCase.signal(conditionCase.condition2), "thread4").start();
    }

    private void await(Condition condition) {
        try {
            lock.lock();
            System.out.println("开始等待await！ ThreadName：" + Thread.currentThread().getName());
            condition.await();
            System.out.println("等待await结束！ ThreadName：" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void signal(Condition condition) {
        lock.lock();
        System.out.println("发送通知signal！ ThreadName：" + Thread.currentThread().getName());
        condition.signal();
        lock.unlock();
    }

}
