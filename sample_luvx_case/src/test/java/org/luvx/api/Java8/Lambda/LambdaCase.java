package org.luvx.api.Java8.Lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class LambdaCase {

    List<String> names = Arrays.asList("peter", "anna", "xenia", "mike");

    /**
     * 普通的排序
     */
    @Test
    public void commonSort() {
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });
        System.out.println(names);
    }

    /**
     * Lamada排序
     */
    @Test
    public void lambdaSort() {
        // Collections.sort(names, (String a, String b) -> {
        // return b.compareTo(a);
        // });

        // 一行代码可以不使用{},return 也不需要
        // Collections.sort(names, (String a, String b) -> b.compareTo(a));

        // 自动识别参数类型,不用声明参数类型
        Collections.sort(names, (a, b) -> a.compareTo(b));
        System.out.println(names);
    }
}