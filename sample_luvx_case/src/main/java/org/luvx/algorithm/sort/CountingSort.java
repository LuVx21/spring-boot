package org.luvx.algorithm.sort;

import org.luvx.utils.ArrayUtils;


public class CountingSort {
    /**
     * 计数排序
     *
     * @param array 待排序数组
     */
    public static void Sort(int[] array) {
        countSort(array, ArrayUtils.maxElement(array));
    }

    private static void countSort(int[] array, int maxElement) {
        int[] countArray = new int[maxElement + 1];

        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            if (val < 0) {
                System.out.println("下标超界");
                return;
            }
            countArray[val] += 1;
        }

        for (int i = 0, index = 0; i < countArray.length; i++) {
            for (int j = 0; j < countArray[i]; j++) {
                array[index++] = i;
            }
        }
    }
}
