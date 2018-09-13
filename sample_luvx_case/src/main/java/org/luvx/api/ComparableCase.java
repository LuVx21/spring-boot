package org.luvx.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ComparableCase implements Comparable<ComparableCase> {

    private int id;

    ComparableCase(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(ComparableCase o) {
        return this.id - o.id;
    }

    @Override
    public String toString() {
        return id + "";
    }

    public static void main(String[] args) {
        ComparableCase case1 = new ComparableCase(4);
        ComparableCase case2 = new ComparableCase(2);
        ComparableCase case3 = new ComparableCase(3);

        ArrayList<ComparableCase> list = new ArrayList<>();
        list.add(case1);
        list.add(case2);
        list.add(case3);

        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);

        ComparableCase[] arr = {case1, case2, case3};
        System.out.println(arr[0]);

        Arrays.sort(arr);
        System.out.println(arr[0]);
    }

}
