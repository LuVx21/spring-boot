package org.luvx.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// 假设此类不可被修改
public final class ComparatorCase {

    private int id;

    ComparatorCase(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "";
    }

    //想要对类ComparatorCase的对象间进行比较,无法实现Comparable接口
    public static void main(String[] args) {
        ComparatorCase case1 = new ComparatorCase(4);
        ComparatorCase case2 = new ComparatorCase(2);
        ComparatorCase case3 = new ComparatorCase(3);

        ArrayList<ComparatorCase> list = new ArrayList<>();
        list.add(case1);
        list.add(case2);
        list.add(case3);

        System.out.println(list);
        Collections.sort(list, new Comparator<ComparatorCase>() {
            @Override
            public int compare(ComparatorCase a, ComparatorCase b) {
                return a.id - b.id;
            }
        });
        System.out.println(list);
    }
}
