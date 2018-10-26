package org.luvx.algorithm.linkarray;

/**
 * 链表节点类
 */
public class Node {
    Node pre;
    Node next;
    int date;

    Node() {
    }

    Node(Node pre, Node next, int date) {
        this.pre = pre;
        this.next = next;
        this.date = date;
    }
}