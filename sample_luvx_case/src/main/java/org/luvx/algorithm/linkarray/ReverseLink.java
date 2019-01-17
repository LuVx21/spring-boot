package org.luvx.algorithm.linkarray;

/**
 * * 1 -> 2 -> 3 -> 4 -> 5
 * 单链表反转
 */
public class ReverseLink {
    /**
     * 单链表反向
     * 1. 原头结点置空
     * 2. 循环结束条件
     *
     * @param head
     * @return
     */
    public static Node reverseLink(Node head) {
        if (head == null || head.next == null)
            return head;

        Node before = head;
        before.next = null;
        head = head.next;

        while (head != null) {
            Node temp = head.next;
            if (temp == null) {
                head.next = before;
                return head;
            }
            head.next = before;
            before = head;
            head = temp;
        }
        return null;
    }

    public static void main(String[] args) {
        Node node5 = new Node(null, null, 5);
        Node node4 = new Node(null, node5, 4);
        Node node3 = new Node(null, node4, 3);
        Node node2 = new Node(null, node3, 2);
        Node node1 = new Node(null, node2, 1);
        LinkUtils.printLink(node1);
        Node head = reverseLink(node1);
        LinkUtils.printLink(head);
    }
}
