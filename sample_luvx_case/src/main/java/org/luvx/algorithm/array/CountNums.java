package org.luvx.algorithm.array;

import org.luvx.algorithm.search.BinarySearch;

/**
 * 有序数组, 计算指定数字的出现次数
 */
public class CountNums {
    /**
     * 计算target出现的次数
     *
     * @param array
     * @param target
     * @return
     */
    public static int countNums(int[] array, int target) {
        int index = BinarySearch.binarySearchByLoop(array, target);
        if (index == -1) {
            return 0;
        }
        int left = index - 1;
        int right = index + 1;

        while (array[left] == target)
            left--;
        while (array[right] == target)
            right++;
        return right - left - 1;
    }

    public static int countNums1(int[] array, int target) {


        return 0;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 3, 4, 5};
        System.out.println(countNums(array, 3));
    }
}
