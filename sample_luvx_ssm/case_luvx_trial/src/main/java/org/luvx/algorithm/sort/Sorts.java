package org.luvx.algorithm.sort;

/**
 * 排序算法
 */
public class Sorts {

    // 简单选择排序
    public static void sort5(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int k = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[k] > array[j])
                    k = j;
            }
            if (k != i) {
                int temp = array[i];
                array[i] = array[k];
                array[k] = temp;
            }
        }
    }

    // 冒泡排序
    public static void sort3(int[] array) {
        boolean flag = false;
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < array.length - i; j++) {
                if (array[j] < array[j - 1]) {
                    flag = true;
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
            if (flag == false)
                return;
        }
    }



}
