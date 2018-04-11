package org.luvx.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;


public class InsertionSortTest {

    @Test
    public void shellSort() {
        int[] arr = {43, 99, 38, 76, 65, 27, 17};
        InsertionSort.shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}