package org.luvx.algorithm.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 桶排序
 */
public class BucketSort {

    /**
     * 桶排序
     *
     * @param sequenceList
     */
    public static void sort(List<Integer> sequenceList) {
        // 1 求取sequence中获取到需要除以的值，返回值为10/100/1000/10000...
        int divisionValueOfSequence = getDivisionValueOfSequence(sequenceList);
        // 2 按照除数来计算
        sort(sequenceList, divisionValueOfSequence);
    }

    // 按照指定的数位来排序
    private static void sort(List<Integer> sequenceList, int divisionValue) {
        // 1 初始化待比较的桶数组, 类型为List<Integer>, 保存的为序列
        List<Integer>[] bucketArray = new List[10];

        // 2 迭代待排序序列，并放到对应的桶中
        for (int value : sequenceList) {
            int index = value / divisionValue % 10;  //通过该运算获取到我们需要排序的那一位数字，并放入到对应桶中
            if (bucketArray[index] == null) bucketArray[index] = new ArrayList<>();
            bucketArray[index].add(value);
        }
        // 4 迭代桶队列，对每一个桶中的数据做排序，我们这里还是采用桶排序(也可以采用其他的排序方式，如插入排序)
        if (divisionValue > 1)
            for (List<Integer> bucketDataList : bucketArray) {
                if (bucketDataList != null && bucketDataList.size() > 0) sort(bucketDataList, divisionValue / 10);
            }
        // 清楚数据，并按照bucket中的重新设置排序好的序列
        sequenceList.clear();
        for (List<Integer> list : bucketArray) {
            if (list != null && list.size() > 0) sequenceList.addAll(list);
        }
    }

    private static int getDivisionValueOfSequence(List<Integer> sequenceArray) {
        // 计算待排序中的最大值
        int max = sequenceArray.get(0);
        for (int target : sequenceArray) {
            if (target > max) max = target;
        }
        // 求取第一次的除数，只会为10^n
        int divisionValue = 1;
        while (max >= 10) {
            divisionValue *= 10;
            max /= 10;
        }
        return divisionValue;
    }
}
