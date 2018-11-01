package org.luvx.algorithm.linkarray;

/**
 * 给定一个单链表,使得奇数位置的元素位于偶数位置元素之前
 * 1->2->4->5->6
 * ↓
 * 1->4->6->2->5
 */
public class Q5 {
    public static Node modifiedLink01(Node head) {
        if (head == null || head.next == null)
            return head;

        Node oddNum = head;
        Node evenNum = head.next;
        Node evenHead = head.next;

        while (evenNum != null && evenNum.next != null) {
            oddNum.next = evenNum.next;
            oddNum = oddNum.next;
            evenNum.next = oddNum.next;
            evenNum = evenNum.next;
        }
        oddNum.next = evenHead;

        return head;
    }

    public static void main(String[] args) {
        Node node6 = new Node(null, null, 5);
        Node node5 = new Node(null, node6, 4);
        Node node4 = new Node(null, node5, 3);
        Node node3 = new Node(null, node4, 2);
        Node node2 = new Node(null, node3, 1);
        Node node1 = new Node(null, node2, 2);
        LinkUtils.printLink(node1);
        Node head = modifiedLink01(node1);
        LinkUtils.printLink(head);
    }
}
