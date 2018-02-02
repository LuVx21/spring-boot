package org.luvx.collection;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

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
}
