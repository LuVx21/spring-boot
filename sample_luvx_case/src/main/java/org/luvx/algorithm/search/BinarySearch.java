package org.luvx.algorithm.search;

/**
 * 二分查找(假设数组递增)
 */
public class BinarySearch {
    /**
     * 递归实现
     *
     * @param array
     */
    public static int binarySearch(int[] array, int start, int end, int i) {
        if (start <= end) {
            int middle = start + (end - start) / 2;
            if (i < array[middle]) {
                return binarySearch(array, start, middle - 1, i);
            } else if (i > array[middle]) {
                return binarySearch(array, middle + 1, end, i);
            } else {
                return middle;
            }
        }
        return -1;
    }

    /**
     * 循环实现
     *
     * @param array
     */
    public static int binarySearchByLoop(int[] array, int i) {
        int start = 0;
        int end = array.length - 1;

        while (start <= end) {
            int middle = start + (end - start) / 2;
            if (i < array[middle]) {
                end = middle - 1;
            } else if (i > array[middle]) {
                start = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        System.out.println(binarySearch(array, 0, array.length - 1, 5));
        System.out.println(binarySearchByLoop(array, 5));
    }
}
