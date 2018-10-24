package org.luvx.algorithm.tree;

import java.util.Stack;

public class BTLeafNum {
    /**
     * 计算二叉树叶子节点的个数
     *
     * @param root
     * @return 个数
     */
    public static int comLeafNums(Node root) {
        if (root == null)
            return 0;
        if (root.isLeaf())
            return 1;

        int leftNums = comLeafNums(root.getLeft());
        int rightNums = comLeafNums(root.getRight());

        return leftNums + rightNums;
    }

    /**
     * 计算二叉树叶子节点的个数:循环方式
     *
     * @param root
     * @return 个数
     */
    public static int comLeafNumsByLoop(Node root) {
        int nums = 0;
        Stack<Node> stack = new Stack<>();
        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            if (stack.isEmpty())
                break;
            root = stack.pop();
            if (root.isLeaf())
                nums++;
            root = root.getRight();
        }

        return nums;
    }
}
