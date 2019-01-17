package org.luvx.api.collections;

import java.util.HashSet;

public class HashSetCase {

    public static void method() {
        HashSet<String> set = new HashSet();
        set.add("a");
        set.add("b");
        set.add("a");

        set.remove("b");

        System.out.println(set);
    }

    public static void main(String[] args) {
        method();
    }
}
