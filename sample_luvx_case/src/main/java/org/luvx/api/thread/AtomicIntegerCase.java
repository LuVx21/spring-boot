package org.luvx.api.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS:原子类
 */
public class AtomicIntegerCase {

    private static AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    // incrementAndGet内部的compareAndSet便是CAS操作
                    System.out.println(integer.incrementAndGet());
                }
            }
        }).start();
    }
}