package org.luvx.algorithm.tree;

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

}
