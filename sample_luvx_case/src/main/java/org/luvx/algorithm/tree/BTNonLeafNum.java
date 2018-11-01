package org.luvx.algorithm.tree;

import java.util.Stack;

public class BTNonLeafNum {
    /**
     * 计算二叉树非叶子节点的个数
     *
     * @param root
     * @return 个数
     */
    public static int comNonLeafNums(Node root) {
        if (root == null || root.isLeaf())
            return 0;

        int leftNums = comNonLeafNums(root.getLeft());
        int rightNums = comNonLeafNums(root.getRight());

        return leftNums + rightNums + 1;
    }

    /**
     * 计算二叉树非叶子节点的个数:循环方式
     *
     * @param root
     * @return 个数
     */
    public static int comNonLeafNumsByLoop(Node root) {
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
            if (!root.isLeaf())
                nums++;
            root = root.getRight();
        }

        return nums;
    }
}
