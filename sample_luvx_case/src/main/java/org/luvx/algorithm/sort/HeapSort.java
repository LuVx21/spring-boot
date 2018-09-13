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
     * 调整大顶堆:写法1
     *
     * @param array  待排序数组
     * @param i
     * @param length
     */
    private static void adjustHeap0(int[] array, int i, int length) {
        int parent = array[i];
        for (int left = i * 2 + 1; left < length; left = left * 2 + 1) {
            if (left + 1 < length && array[left] < array[left + 1]) {
                left++;
            }
            if (array[left] > parent) {
                array[i] = array[left];
                i = left;
            } else {
                break;
            }
        }
        array[i] = parent;
    }

    /**
     * 调整大顶堆:写法2(本质和1相同,但容易理解)
     *
     * @param array  待排序数组
     * @param i
     * @param length
     */
    private static void adjustHeap(int[] array, int i, int length) {
        int parent = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < length && array[left] > array[parent])
            parent = left;
        if (right < length && array[right] > array[parent])
            parent = right;

        if (parent != i) {
            ArrayUtils.arraySwap(array, i, parent);
            adjustHeap(array, parent, length);
        }
    }
}
