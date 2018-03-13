package org.luvx.algorithm.sort;

import java.util.Arrays;

public class InsertionSort {

    /**
     * 直接插入排序
     *
     * @param array
     */
    public static void insertionSort(int[] array) {
        int length = array.length;
        for (int i = 1; i < length; i++) {
            int temp = array[i];
            int j = i - 1;
            while (j >= 0 && (array[j] > temp)) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = temp;
        }
    }

    /**
     * 希尔排序
     *
     * @param array
     */
    public static void shellSort(int[] array) {
        int len = array.length / 2;
        while (len >= 1)// 分组,每组元素为len个
        {
            for (int k = 0; k < len; k++)// 组内进行排序
            {
                for (int i = k + len; i < array.length; i += len) {
                    int temp = array[i];
                    int j = i - len;// 前一个
                    while (j >= k && array[j] > temp) {
                        array[j + len] = array[j];
                        j -= len;
                    }
                    array[j + len] = temp;
                } // 当len = 1 时 可看做直接插入排序
            }
            len = len / 2;// 每组元素为原来的一半
        }
    }


    /**
     * 折半插入排序
     *
     * @param array
     */
    public static void binInsertionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int temp = array[i];
            int left = 0;
            int right = i - 1;
            while (left <= right) {
                int middle = (left + right) / 2;
                if (array[middle] > temp)
                    right = middle - 1;
                else
                    left = middle + 1;
            }
            for (int j = i - 1; j >= left; j--)
                array[j + 1] = array[j];
            array[left] = temp;
        }
    }


    public static void main(String[] args) {
        int[] arr = {43, 99, 38, 76, 65, 27, 17};
        // insertionSort(arr);
        // binInsertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
