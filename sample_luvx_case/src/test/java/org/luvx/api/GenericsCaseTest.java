package org.luvx.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型使用
 */
public class GenericsCaseTest {
    /**
     * 使用了上限通配符的集合等,不能向集合中添加元素
     */
    @Test
    public void run01() {
        List<Number> list = new ArrayList<Number>();
        getNumbers(list);
    }

    /**
     * 使用了下限通配符的集合等,不能从集合中取元素
     */
    @Test
    public void run02() {
        List<Integer> list = new ArrayList<Integer>();
        setNumbers(list);
    }

    public void getNumbers(List<? extends Number> list) {
        // list.add(100); //编译报错
        list.add(null);// 特例
        list.get(0);
    }

    public void setNumbers(List<? super Integer> list) {
        list.add(100);
        // Integer i = list.get(0); //编译报错
        Object o = list.get(0);// 特例
    }


}
