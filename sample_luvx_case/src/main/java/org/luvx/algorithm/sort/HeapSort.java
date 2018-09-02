package org.luvx.algorithm.sort;

import org.luvx.utils.ArrayUtils;

/**
 * 堆排序算法
 * <p>
 * 1. 构建大顶堆
 * 2. 调整堆结构+交换堆顶元素与末尾元素
 */
public class HeapSort {
    /**
     * 堆排序算法
     *
     * @param array 待排序数组
     */
    public static void sort0(int[] array) {
        int length = array.length;
        for (int i = length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, length);
        }

        for (int j = length - 1; j > 0; j--) {
            ArrayUtils.arraySwap(array, 0, j);
            adjustHeap(array, 0, j);
        }
    }

    /**
     * 调整大顶堆
     *
     * @param array  待排序数组
     * @param i
     * @param length
     */
    private static void adjustHeap(int[] array, int i, int length) {
        int parent = array[i];
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }
            if (array[k] > parent) {
                array[i] = array[k];
                i = k;
            } else {
                break;
            }
        }
        array[i] = parent;
    }
}
