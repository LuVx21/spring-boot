package org.luvx.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ArrayList:
 * 允许空
 * 允许重复
 * 有序
 * 非线程安全
 */
public class ArrayListCase {

    @Test
    public void run0() {
        List<String> list = new ArrayList<String>();
        /**
         * 扩容校验->grow()扩容1.5倍
         */
        list.add("1");
        list.get(0);
        list.set(0, "a");
        /**
         * 下标校验->获取旧值->移动->置空
         */
        list.remove(0);
        System.out.println(list);
    }

    /**
     * 适用于多线程环境下的方式
     */
    @Test
    public void run01() {
        List<String> list = Collections.synchronizedList(new ArrayList<String>());
    }
}
