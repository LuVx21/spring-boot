package org.luvx.utils;

public class ArrayUtils {

    public static void arraySwap(int[] array, int from, int to) {
        int temp = array[from];
        array[from] = array[to];
        array[to] = temp;
    }
}
