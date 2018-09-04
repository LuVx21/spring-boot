package org.luvx.algorithm.sort;

import org.luvx.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 从个位数开始排序
 */
public class RadixSort {
    /**
     * 基数排序
     *
     * @param array 待排序数组
     */
    public static void Sort(int[] array) {
        int max = ArrayUtils.maxElement(array);

        for (int exp = 1; max / exp > 0; exp *= 10)
            countSort(array, exp);
    }

    private static void countSort(int[] array, int exp) {
        int length = array.length;
        int[] output = new int[length];
        int[] countArray = new int[10];

        for (int i = 0; i < length; i++)
            countArray[(array[i] / exp) % 10]++;

        for (int i = 1; i < 10; i++)
            countArray[i] += countArray[i - 1];

        // 为了稳定性而使用倒序
        for (int i = length - 1; i >= 0; i--) {
            output[countArray[(array[i] / exp) % 10] - 1] = array[i];
            countArray[(array[i] / exp) % 10]--;
        }

        for (int i = 0; i < length; i++)
            array[i] = output[i];

        output = null;
        countArray = null;
    }
}