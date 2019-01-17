package org.luvx.api.collections;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 理解List的asList方法
 */
public class List_asListCase {

    public static void method() {
        // 输出obj.toString()
        int[] array = {10, 20, 20};
        List list = Arrays.asList(array);
        System.out.println(list.toString());

        list = Arrays.asList(ArrayUtils.toObject(array));
        System.out.println(list.toString());
    }

    public static void method1() {
        // 输出元素
        Integer[] array = {10, 20, 30};

        List list = Arrays.asList(array);
        System.out.println(list.toString());
    }

    public static void main(String[] args) {
        method();
        System.out.println("---------");
        method1();
    }
}
