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

    /**
     * 实现1:不使用新的数组
     *
     * @param array
     * @param maxElement
     */
    private static void countSort(int[] array, int maxElement) {
        int[] countArray = genCountArray(array, maxElement);

        for (int i = 0, index = 0; i < countArray.length; i++) {
            for (int j = 0; j < countArray[i]; j++) {
                array[index++] = i;
            }
        }
    }

    /**
     * 实现2:使用新的数组
     *
     * @param array
     * @param maxElement
     */
    private static void countSort1(int[] array, int maxElement) {
        int[] countArray = genCountArray(array, maxElement);
        int[] tempArray = new int[array.length];

        getIndex(countArray);

        // 为了稳定性而使用倒序
        for (int i = array.length - 1; i >= 0; i--) {
            // 改为使用jdk提供的方法
            // tempArray[countArray[array[i]]] = array[i];
            System.arraycopy(array, i, tempArray, countArray[array[i]], 1);
            countArray[array[i]]--;
        }

        System.arraycopy(tempArray, 0, array, 0, array.length);
    }


    /**
     * 作成计数数组:存储了待排序数组中元素出现的次数
     *
     * @param array      待排序数组
     * @param maxElement
     * @return
     */
    private static int[] genCountArray(int[] array, int maxElement) {
        int[] countArray = new int[maxElement + 1];

        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            if (val < 0) {
                System.out.println("下标超界");
                return null;
            }
            countArray[val] += 1;
        }
        return countArray;
    }

    /**
     * 将计数数组整理为存储待排序数组元素应在的位置
     *
     * @param countArray 计数数组
     */
    private static void getIndex(int[] countArray) {
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        for (int i = 0; i < countArray.length; i++) {
            if (countArray[i] > 0) {
                countArray[i]--;
            }
        }
    }
}
