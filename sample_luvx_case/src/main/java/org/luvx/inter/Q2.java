package org.luvx.inter;

/**
 * 一个数组先上升后下降,找到最大值
 * QA:
 * 出现多个峰值符合先升后降吗?
 * 单调递增或递减怎么算
 * 中间有相同的怎么算
 */
public class Q2 {
    /**
     * 不适用于多个峰值
     *
     * @param array
     * @return
     */
    public static int findMax0(int[] array) {
        for (int i = 0, j = array.length - 1; i < j; ) {
            if (array[i] <= array[i + 1]) {
                i++;
            } else {
                return array[i];
            }

            if (array[j] <= array[j - 1]) {
                j--;
            } else {
                return array[j];
            }
        }
        return Math.max(array[0], array[array.length - 1]);
    }

    public static int findMax(int[] array) {
        int start = 0;
        int end = array.length - 1;
        int middle = start + (end - start) / 2;

        while (middle > 0 && middle < array.length - 1) {
            if (array[middle] > array[middle - 1] && array[middle] > array[middle + 1]) {
                return array[middle];
            } else if (array[middle] < array[middle + 1]) {
                start = middle + 1;
                middle = start + (end - start) / 2;
            } else {
                end = middle - 1;
                middle = start + (end - start) / 2;
            }
        }
        return Math.max(array[0], array[array.length - 1]);
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 6, 5, 4};
        System.out.println(findMax0(array));
        System.out.println(findMax(array));
    }
}
