package org.luvx.algorithm.sort;

public class SortUtils {



    /**
     * 冒泡排序
     *
     * @param array 排序数组
     */
    public static void bubbleSort(int[] array) {
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
     * 是否有序
     *
     * @param array
     * @return
     */
    public boolean isSorted(int[] array) {
        return isAscSorted(array) && isDescSorted(array);
    }

    /**
     * 是否升序
     *
     * @param array
     * @return
     */
    public boolean isAscSorted(int[] array) {
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
    public boolean isDescSorted(int[] array) {
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


}
