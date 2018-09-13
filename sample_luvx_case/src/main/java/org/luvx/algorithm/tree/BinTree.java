package org.luvx.algorithm.tree;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 二叉树
 */
public class BinTree {

    /**
     * 前序遍历
     *
     * @param root 根节点
     */
    public static void preSearch(Node root) {
        if (root == null)
            return;
        System.out.println(root.getData());
        preSearch(root.getLeft());
        preSearch(root.getRight());
    }

    /**
     * 中序遍历
     *
     * @param root 根节点
     */
    public static void inSearch(Node root) {
        if (root == null)
            return;
        inSearch(root.getLeft());
        System.out.println(root.getData());
        inSearch(root.getRight());
    }

    /**
     * 后序遍历
     *
     * @param root 根节点
     */
    public static void postSearch(Node root) {
        if (root == null)
            return;
        postSearch(root.getLeft());
        postSearch(root.getRight());
        System.out.println(root.getData());
    }

    /**
     * 层级遍历
     *
     * @param root
     */
    public static void levelSearch(Node root) {
        Queue<Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.add(root);
        Node temp = null;
        int currentLevel = 1;    //记录当前层需要打印的节点的数量
        int nextLevel = 0;//记录下一层需要打印的节点的数量
        while ((temp = nodeQueue.poll()) != null) {
            if (temp.getLeft() != null) {
                nodeQueue.add(temp.getLeft());
                nextLevel++;

            }
            if (temp.getRight() != null) {
                nodeQueue.add(temp.getRight());
                nextLevel++;
            }
            System.out.print(temp.getData() + " ");
            currentLevel--;
            if (currentLevel == 0) {
                System.out.println();
                currentLevel = nextLevel;
                nextLevel = 0;
            }
        }
    }

}
