package org.luvx.algorithm.linkarray;

/**
 * 链表去重复:
 * 0. 保留第一个重复的
 * 1. 不保留重复元素
 * 2. 保留最后一个重复元素
 */
public class RemoveMul {
    // 保留第一个元素
    public static Node removeMul0(Node head) {
        Node before = head;
        Node current = null;

        while (before != null) {
            current = before.next;
            if (current != null && current.data == before.data) {
                before.next = current.next;
            } else {
                before = current;
            }
        }

        return head;
    }

    // 不保留重复元素
    public static Node removeMul1(Node head) {
        while (head != null && head.data == head.next.data) {
            head = head.next.next;
        }

        Node before = head;
        Node current = before.next;
        Node next;
        boolean flag;

        while (current != null) {
            next = current.next;
            flag = false;
            while (next != null && current.data == next.data) {
                before.next = next;
                current = next;
                next = current.next;
                flag = true;
            }
            if (flag) {
                before.next = next;
            } else {
                before = current;
            }
            current = next;
        }
        return head;
    }

    public static Node removeMul2(Node head) {
        return head;
    }

    public static void main(String[] args) {
        // 1 -> 1 -> 1 -> 3 -> 4 -> 4 -> 5
        Node node7 = new Node(null, null, 5);
        Node node6 = new Node(null, node7, 4);
        Node node5 = new Node(null, node6, 4);
        Node node4 = new Node(null, node5, 3);
        Node node3 = new Node(null, node4, 1);
        Node node2 = new Node(null, node3, 1);
        Node node1 = new Node(null, node2, 1);
        LinkUtils.printLink(node1);
        Node head = removeMul1(node1);
        // 3 5
        LinkUtils.printLink(head);
    }
}
