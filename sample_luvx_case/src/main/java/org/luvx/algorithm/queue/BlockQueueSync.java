package org.luvx.algorithm.queue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockQueueSync {

    private final LinkedList<Object> list = new LinkedList<>();
    private final AtomicInteger count = new AtomicInteger(0);
    private final int MAXSIZE = 5;
    private final int MINSIZE = 0;

    private final Object lock = new Object();

    public void put(Object obj) {
        synchronized (lock) {
            while (count.get() == MAXSIZE) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(obj);
            count.getAndIncrement();
            System.out.println(" 元素 " + obj + " 被添加 ");
            lock.notify();
        }
    }

    public Object get() {
        Object temp;
        synchronized (lock) {
            while (count.get() == MINSIZE) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count.getAndDecrement();
            temp = list.removeFirst();
            System.out.println(" 元素 " + temp + " 被移出 ");
            lock.notify();
        }
        return temp;
    }

    private int size() {
        return count.get();
    }

    public static void main(String[] args) throws Exception {

        final BlockQueueSync myQueue = new BlockQueueSync();
        initMyQueue(myQueue);

        Thread t1 = new Thread(() -> {
            myQueue.put("h");
            myQueue.put("i");
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                myQueue.get();
                Thread.sleep(2000);
                myQueue.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        Thread.sleep(1000);
        t2.start();
    }

    private static void initMyQueue(BlockQueueSync myQueue) {
        myQueue.put("a");
        myQueue.put("b");
        myQueue.put("c");
        myQueue.put("d");
        myQueue.put("e");
        System.out.println("当前元素个数：" + myQueue.size());
    }
}