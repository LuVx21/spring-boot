package org.luvx.algorithm.tree;

/**
 * 二叉搜索树
 */
public class BinSearchTree {
    /**
     * 递归实现
     *
     * @param root 根节点
     * @param obj  查找值
     */
    public static void search(Node<Integer> root, Integer obj) {
        if (root == null)
            return;
        if (root.getData() == obj) {
            System.out.println("OK");
        } else if (obj < root.getData()) {
            System.out.println("左子树......");
            search(root.getLeft(), obj);
        } else {
            System.out.println("右子树......");
            search(root.getRight(), obj);
        }
    }




}
