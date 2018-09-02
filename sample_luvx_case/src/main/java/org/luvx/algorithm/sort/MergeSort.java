package org.luvx.algorithm.sort;

/**
 * 归并排序
 */
public class MergeSort {

    public static void mergeSort(int[] array) {
        MergeSortRecursion(array, 0, array.length - 1);
        // MergeSortIteration(array, array.length);
    }

    /**
     * 递归实现的归并排序(自顶向下)
     *
     * @param array 待排序数组
     * @param left  起点
     * @param right 终点
     */
    public static void MergeSortRecursion(int[] array, int left, int right) {
        if (left == right)
            return;
        int mid = (left + right) / 2;
        MergeSortRecursion(array, left, mid);
        MergeSortRecursion(array, mid + 1, right);
        Merge(array, left, mid, right);
    }

    /**
     * 非递归(迭代)实现的归并排序(自底向上)
     *
     * @param array  待排序数组
     * @param length 长度
     */
    public static void MergeSortIteration(int[] array, int length) {
        int left, mid, right;
        // 子数组的大小i初始为1，每轮翻倍
        for (int i = 1; i < length; i *= 2) {
            left = 0;
            // 后一个子数组存在(需要归并)
            while (left + i < length) {
                mid = left + i - 1;
                // 后一个子数组大小可能不够
                right = mid + i < length ? mid + i : length - 1;
                Merge(array, left, mid, right);
                // 前一个子数组索引向后移动
                left = right + 1;
            }
        }
    }

    /**
     * 合并子有序数组
     *
     * @param array 待排序数组
     * @param left  起点
     * @param mid   中点
     * @param right 终点
     */
    private static void Merge(int[] array, int left, int mid, int right) {
        int length = right - left + 1;
        int[] temp = new int[length];

        int index = 0;
        int i = left;
        int j = mid + 1;

        while (i <= mid && j <= right) {
            // 带等号保证归并排序的稳定性
            temp[index++] = array[i] <= array[j] ? array[i++] : array[j++];
        }
        while (i <= mid) {
            temp[index++] = array[i++];
        }
        while (j <= right) {
            temp[index++] = array[j++];
        }

        for (int k = 0; k < length; k++) {
            array[left++] = temp[k];
        }
    }
}
