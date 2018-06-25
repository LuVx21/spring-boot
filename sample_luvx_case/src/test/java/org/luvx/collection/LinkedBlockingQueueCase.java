package org.luvx.collection;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列:为空时读元素则会进入等待,知道notEmpty.signal()
 * 链表
 */
public class LinkedBlockingQueueCase {


    @Test
    public void run() {
        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        // ReentrantLock  加锁
        queue.offer("a");
        System.out.println(queue);
        // ReentrantLock 加锁,即增删都要加锁
        Object poll = queue.poll();
        System.out.println(poll);

    }
}
