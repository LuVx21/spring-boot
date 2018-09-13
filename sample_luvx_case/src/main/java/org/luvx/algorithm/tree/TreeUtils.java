package org.luvx.algorithm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeUtils {

    /**
     * 将数组构建成完全二叉树
     *
     * @param array
     */
    private static <T> Node<T> buildCBT(T[] array) {
        int length;

        if ((length = array.length) == 1)
            return new Node<>(array[0]);

        List<Node<T>> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(new Node<>(array[i]));
        }

        int temp = 0;
        while (temp <= (length - 1 - 1) / 2) {
            if (temp * 2 + 1 < length) {
                list.get(temp).setLeft(list.get(temp * 2 + 1));
            }
            if (temp * 2 + 2 < length) {
                list.get(temp).setRight(list.get(temp * 2 + 2));
            }
        }

        return list.get(0);

    }

    /**
     * 将完全二叉树构建成堆
     *
     * @param root
     * @param <T>
     */
    private static <T> void buildCBTtoHeap(Node<T> root) {

    }
}
