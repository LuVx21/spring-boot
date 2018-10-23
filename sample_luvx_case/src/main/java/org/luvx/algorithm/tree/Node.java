package org.luvx.algorithm.tree;

/**
 * 二叉树节点类
 *
 * @param <T>
 */
public class Node<T> {

    private Node<T> left;
    private Node<T> right;
    private T data;

    Node() {
    }

    Node(T data) {
        this.data = data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isLeaf() {
        return this.getLeft() == null && this.getRight() == null;
    }
}
