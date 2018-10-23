package org.luvx.api.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * 理解List的subList方法
 */
public class List_subListCase {
    /**
     * 对sublist的修改都会影响到原list
     */
    public static void method() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        List<Integer> subList = list.subList(2, list.size());

        System.out.println(list);
        System.out.println(subList);

        subList.add(99);
        System.out.println(list);
        System.out.println(subList);

        subList.set(3, 98);
        System.out.println(list);
        System.out.println(subList);

        subList.remove(3);
        System.out.println(list);
        System.out.println(subList);

        // remove效果同样
        list.add(100);
        System.out.println(list);
        // 使用subList()后,尽量避免增删原list后对subList增删改查或获取size
        // 上述操作都会抛出ConcurrentModificationException
        // System.out.println(subList);
    }

    public static void main(String[] args) {
        method();
    }
}
