package org.luvx.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;


public class SortsTest {

    int[] arr = {1, 2, 3, 4, 5, 6, 7};
    // int[] arr = {43, 99, 38, 76, 65, 27, 17};
    // int[] arr = {3, 44, 38, 5, 47, 15, 36, 36, 27, 2, 46, 4, 19, 50, 48};
    // int[] arr = { 45, 32, 98, 74, 17, 53, 44, 61, 22 };

    @Test
    public void sort5Test() {
        System.out.println("排序前:" + Arrays.toString(arr));
        System.out.println("排序后:[17, 27, 38, 43, 65, 76, 99]");
        Sorts.sort5(arr);
        System.out.println("排序后:" + Arrays.toString(arr));
    }

    @Test
    public void heapSortTest() {
        System.out.println(Arrays.toString(arr));
        HeapSort.sort0(arr);
        System.out.println(Arrays.toString(arr));
    }

}