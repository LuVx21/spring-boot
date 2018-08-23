package org.luvx.algorithm.sort;

/**
 * 冒泡排序
 * 以下为升序排序
 */
public class BubbleSort {

    /**
     * 冒泡排序
     *
     * @param array 排序数组
     */
    public static void sort0(int[] array) {
        int length = array.length;

        for (int i = 0; i < length; i++) {
            for (int j = 1; j < length - i; j++) {
                if (array[j - 1] > array[j]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
    }

    /**
     * 优化1:当某一次遍历中一次交换都没有发生,
     * 则已经达到有序, 不必进行剩下的比较
     * 优化了左边已有序时的情形
     *
     * @param array 排序数组
     */
    public static void sort1(int[] array) {
        int length = array.length;

        for (int i = 0; i < length; i++) {
            boolean isSorted = true;
            for (int j = 1; j < length - i; j++) {
                if (array[j - 1] > array[j]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;

                    isSorted = false;
                }
            }
            if (isSorted)
                break;
        }
    }

    /**
     * 优化2:确定有序区边界
     * 优化右边已有序时的情形
     * 如:3,4,2,1,5,6,7,8
     *
     * @param array 排序数组
     */
    public static void sort2(int[] array) {
        int length = array.length;
        int maxIndex = length - 1;
        // 有序区起点
        int startIndex = -1;

        for (int i = 0; i < length; i++) {
            boolean isSorted = true;
            for (int j = 1; j < maxIndex; j++) {
                if (array[j - 1] > array[j]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;

                    startIndex = j;
                    isSorted = false;
                }
            }
            maxIndex = startIndex;
            if (isSorted)
                break;
        }
    }

    /**
     * 鸡尾酒排序(定向冒泡排序)
     * 排序时是以双向在序列中进行排序
     *
     * @param array 排序数组
     */
    public static void cocktailSort(int[] array) {
        int length = array.length;

        for (int i = 0; i < length / 2; i++) {
            for (int j = i; j < length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            for (int j = length - 1 - (i + 1); j > i; j--) {
                if (array[j] < array[j - 1]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
    }
}
