package org.luvx.mapper;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenRenTest {

    @Test
    public void selectSelective1() {
        // 构造list方式1
        ArrayList<String> list = Lists.newArrayList("1", "12", "13", "14", "15", "0");

        // 构造list方式2
        // ArrayList<String> list = new ArrayList<>();
        // list.add("1");
        // list.add("12");
        // list.add("13");
        // list.add("14");
        // list.add("15");
        // list.add("0");

        // 构造list方式3:会出错, 为什么?
        // List<String> list = Arrays.asList("1", "12", "13", "14", "15", "0");

        System.out.println("初始时：" + list.toString());
        list.removeIf(s -> s.contains("2"));
        System.out.println("过滤完：" + list.toString());

    }

    @Test
    public void arrayTest() {
        // Arrays.asList返回的不是java.util.ArrayList, 而是Arrays内部类ArrayList. 没有重写clear()方法
        List<String> list = Arrays.asList("a", "b", "c");
        // 会抛出java.lang.UnsupportedOperationException异常
        list.clear();
    }

}