package org.luvx.api.collections;

import java.util.TreeSet;

/**
 * @ClassName: org.luvx.api.collections
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 15:32
 */
public class TreeSetCase {

    public static void main(String[] args) {
        TreeSet<String> set = new TreeSet<>();
        set.add("aa");
        set.add("bb");
        set.add("cc");
        System.out.println(set);

        set.last();
        set.first();
        // set.pollFirst();
        // set.pollLast();
        System.out.println(set.remove("cc"));
        System.out.println(set.remove("dd"));

        // NavigableSet<String> newset = Collections.synchronizedNavigableSet(set);
    }
}
