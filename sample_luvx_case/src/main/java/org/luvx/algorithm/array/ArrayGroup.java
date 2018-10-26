package org.luvx.algorithm.array;

import com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator;

/**
 * int型数组中，任取2个数据将数组分成3个区域的数据和相等
 * 请找出这2个数据
 */
public class ArrayGroup {
    public static void main(String[] args) {
        // 4,5
        int[] array = {1, 2, 3, 4, 5, 1, 5, 4, 2};
        arrayGroup(array);
    }

    public static void arrayGroup(int[] array) {
        int length = array.length;
        if (length <= 4) {
            System.out.println("不存在!");
            return;
        }
        for (int i = 1; i <= length - 3; i++) {
            int left = sumArray(array, 0, i - 1);
            for (int j = i + 1; j <= length - 3; j++) {
                int middle = sumArray(array, i + 1, j);
                if (left == middle) {
                    int right = sumArray(array, j + 2, length - 1);
                    if (left == right) {
                        System.out.println("存在" + array[i] + ":" + array[j + 1]);
                    } else {
                        System.out.println("不存在!");
                    }
                }
            }
        }
    }

    /**
     * 数组array中[start,end]范围内元素和
     *
     * @param array 数组
     * @param start 起点
     * @param end   终点
     * @return 和
     */
    public static int sumArray(int[] array, int start, int end) {
        int sum = 0;
        while (start <= end) {
            sum += array[start];
            start++;
        }
        return sum;
    }
}
