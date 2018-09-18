package org.luvx.inter;

import org.apache.commons.lang3.ArrayUtils;
import org.luvx.algorithm.sort.MergeSort;

import java.util.Arrays;

/**
 * 数组的交集
 */
public class ArrayIntersecte {
    /**
     * 数组交集:直接双层循环比对
     *
     * @param array1
     * @param array2
     * @return
     */
    public static int[] intersecte(int[] array1, int[] array2) {
        if (array1.length == 0 || array2.length == 0)
            return new int[0];

        int length = Math.min(array1.length, array2.length);
        int[] result = new int[length];
        int index = 0;

        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array2.length; j++) {
                if (!ArrayUtils.contains(result, array1[i]) && array1[i] == array2[j]) {
                    result[index++] = array1[i];
                    break;
                }
            }
        }
        return Arrays.copyOf(result, index);
    }

    /**
     * 数组交集:先排序,再交集
     *
     * @param array1
     * @param array2
     * @return
     */
    public static int[] intersecte1(int[] array1, int[] array2) {
        if (array1.length == 0 || array2.length == 0)
            return new int[0];
        MergeSort.mergeSort(array1);
        MergeSort.mergeSort(array2);

        int length = Math.min(array1.length, array2.length);
        int[] result = new int[length];
        int i = 0, j = 0, index = 0;
        while (i < array1.length && j < array2.length) {
            if (array1[i] < array2[j]) {
                i++;
            } else if (array1[i] > array2[j]) {
                j++;
            } else if (!ArrayUtils.contains(result, array1[i])) {
                result[index++] = array1[i];
                i++;
                j++;
            }
        }

        return Arrays.copyOf(result, index);
    }
}
