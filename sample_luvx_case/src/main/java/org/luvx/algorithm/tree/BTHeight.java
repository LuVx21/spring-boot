package org.luvx.algorithm.tree;

import java.util.LinkedList;

/**
 * 实现计算二叉树的高
 */
public class BTHeight {
    /**
     * 递归方式
     *
     * @param root
     * @return
     */
    public static int comHeight(Node root) {
        if (root == null)
            return 0;

        int leftHeight = comHeight(root.getLeft());
        int rightHeight = comHeight(root.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 循环方式
     *
     * @param root
     * @return
     */
    public static int comHeightByLoop(Node root) {
        if (root == null)
            return 0;
        Node head = null;
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);

        int index, elementNums;
        int level = 0;

        while (!queue.isEmpty()) {
            index = 0;
            elementNums = queue.size();
            while (index < elementNums) {
                head = queue.poll();
                index++;
                if (head.getLeft() != null)
                    queue.offer(head.getLeft());
                if (head.getRight() != null)
                    queue.offer(head.getRight());
            }
            level++;
        }
        return level;
    }
}
