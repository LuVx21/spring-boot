package org.luvx.algorithm.sort;


public class SelectSort {
    /**
     * 直接选择排序
     *
     * @param array
     */
    public static void selectSort(int[] array) {
        int length = array.length;
        int minIndex;
        for (int i = 0; i < length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }
}
