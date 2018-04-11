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
        int length = array.length;
        // 分割的组数
        int groups = 2;
        int nums = length / groups;
        while (nums >= 1) {
            // 循环的次数和组数相同
            for (int i = 0; i < groups; i++) {
                for (int j = i + nums; j < length; j += nums) {
                    int temp = array[j];
                    int k = j - nums;
                    // 右移数据用循环
                    while (k >= i && array[k] > temp) {
                        array[k + nums] = array[k];
                        k -= nums;
                    }
                    // 插入数据
                    array[k + nums] = temp;
                }
            }
            nums = nums / 2;
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
