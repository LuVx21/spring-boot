package org.luvx.utils;

import java.util.List;

public class ArrayUtils {
    public static int[] initArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 10);
        }
        return array;
    }

    public static void arraySwap(int[] array, int from, int to) {
        int temp = array[from];
        array[from] = array[to];
        array[to] = temp;
    }

    public static int[] listToArray(List list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = (int) list.get(i);
        }
        return array;
    }

    /**
     * 是否有序
     *
     * @param array
     * @return
     */
    public static boolean isSorted(int[] array) {
        return isAscSorted(array) || isDescSorted(array);
    }

    /**
     * 是否升序
     *
     * @param array
     * @return
     */
    public static boolean isAscSorted(int[] array) {
        int i = 0;
        while (i < array.length - 1) {
            if (array[i] <= array[i + 1]) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否降序
     *
     * @param array
     * @return
     */
    public static boolean isDescSorted(int[] array) {
        int i = 0;
        while (i < array.length - 1) {
            if (array[i] >= array[i + 1]) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组中最大元素
     *
     * @param array
     * @return
     */
    public static int maxElement(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     * 数组中最小元素
     *
     * @param array
     * @return
     */
    public static int minElement(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (min > array[i]) {
                min = array[i];
            }
        }
        return min;
    }
}
