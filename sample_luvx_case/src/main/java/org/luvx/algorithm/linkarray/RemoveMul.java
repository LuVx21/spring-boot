package org.luvx.algorithm.linkarray;

/**
 * 1 -> 1 -> 3 -> 4 -> 4 -> 5
 * 删除链表中的重复元素
 */
public class RemoveMul {
    /**
     * @param head 头结点
     * @return 删除后头结点
     */
    public static Node removeMul(Node head) {
        Node current = head;
        Node first = head;

        while (current != null) {


        }


        return null;
    }

    public static void main(String[] args) {
        Node node6 = new Node(null, null, 5);
        Node node5 = new Node(null, node6, 4);
        Node node4 = new Node(null, node5, 4);
        Node node3 = new Node(null, node4, 3);
        Node node2 = new Node(null, node3, 1);
        Node node1 = new Node(null, node2, 1);
        LinkUtils.printLink(node1);
        Node head = removeMul(node1);
        // 3 5
        LinkUtils.printLink(head);
    }
}
