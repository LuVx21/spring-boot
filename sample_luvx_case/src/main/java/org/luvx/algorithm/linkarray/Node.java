package org.luvx.algorithm.linkarray;

/**
 * 链表节点类
 */
public class Node {
    Node pre;
    Node next;
    int data;

    Node() {
    }

    Node(Node pre, Node next, int data) {
        this.pre = pre;
        this.next = next;
        this.data = data;
    }
}