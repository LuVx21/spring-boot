package org.luvx.collection;


import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList集合
 *
 * @author renxie
 */
public class CopyOnWriteArrayListCase {

    /**
     * 线程安全集合
     * 牺牲写效率保证读效率
     * 修改时拷贝一个新数组,对新数组进行修改
     */
    @Test
    public void run0() {
        // 不支持指定长度创建对象
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        // 锁->创建新数组->末尾添加->释放锁
        list.add("a");
        // 无特殊
        list.get(0);
        // 锁->取旧值->旧值和新值不同,创建新数组->修改,释放锁
        list.set(0, "b");
        // 基本同上
        list.remove(0);
        System.out.println(list);
    }


}
