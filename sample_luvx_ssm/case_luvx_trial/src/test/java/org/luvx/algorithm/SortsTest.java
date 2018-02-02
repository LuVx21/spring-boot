package org.luvx.algorithm;

import org.junit.Test;

import java.util.Arrays;


public class SortsTest {

    int[] arr = {43, 99, 38, 76, 65, 27, 17};
    // int[] arr = { 45, 32, 98, 74, 17, 53, 44, 61, 22 };

    @Test
    public void sort5Test() {
        System.out.println("排序前:" + Arrays.toString(arr));
        System.out.println("排序后:[17, 27, 38, 43, 65, 76, 99]");
        Sorts.sort5(arr);
        System.out.println("排序后:" + Arrays.toString(arr));
    }

    @Test
    public void sort4Test() {
    }

    @Test
    public void sort3Test() {
    }

    @Test
    public void sort2Test() {
    }

    @Test
    public void sort1Test() {
    }

    @Test
    public void sortTest() {
    }
}