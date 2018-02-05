package org.luvx.collection;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * LinkedList:
 * 允许空
 * 允许重复
 * 有序
 * 非线程安全
 */
public class LinkedListCase {
    @Test
    public void run0() {
        List<String> list = new LinkedList<String>();
        list.add("a");
        /**
         *根据下标选择从前还是从后开始查找
         */
        list.get(0);
        list.set(0, "b");
        /**
         * 下标校验->解绑节点
         */
        list.remove(0);
        // list.remove("a");
        System.out.println(list);
    }

    /**
     * 队列
     * 基于链表的队列
     */
    @Test
    public void queueCase() {
        Queue<String> queue = new LinkedList<String>();
        queue.offer("a");
        queue.offer("b");
        System.out.println(queue);
    }
}
