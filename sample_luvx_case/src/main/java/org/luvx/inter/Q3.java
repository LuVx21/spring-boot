package org.luvx.inter;

/**
 * 链表去掉奇数位节点
 */
public class Q3 {
    static class Node {
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

    /**
     * 移除奇数位节点:双向链表
     *
     * @param head
     * @return 头节点
     */
    public static Node removeSingleNode(Node head) {
        int index = 1;
        Node current = head;

        while (current != null) {
            if (index % 2 != 0) {
                if (index == 1) {
                    head = head.next;
                    current = current.next;
                    index++;
                    continue;
                }
                current.pre.next = current.next;
                current = current.next;
            } else {
                current = current.next;
                index++;
                continue;
            }
        }
        return head;
    }

    /**
     * 移除奇数位节点:单向链表
     *
     * @param head
     * @return
     */
    public static Node removeSingleNode1(Node head) {
        int index = 1;
        Node current = head;

        while (current != null && current.next != null) {
            if (index % 2 != 0) {
                if (index == 1) {
                    head = head.next;
                    current = head;
                    index++;
                    continue;
                }
                current.next = current.next.next;
                current = current.next.next;
            } else {
                index++;
                continue;
            }
        }
        return head;
    }

    /**
     * 遍历链表
     *
     * @param head
     */
    public static void printLink(Node head) {
        while (head != null) {
            System.out.print(head.date + " ");
            head = head.next;
        }
        System.out.println("\n--------------");
    }

    public static void main(String[] args) {
        Node node4 = new Node(null, null, 4);
        Node node3 = new Node(null, node4, 3);
        Node node2 = new Node(null, node3, 2);
        Node node1 = new Node(null, node2, 1);
        node4.pre = node3;
        node3.pre = node2;
        node2.pre = node1;

        printLink(node1);
        Node head = removeSingleNode(node1);
        printLink(head);
        head = removeSingleNode1(head);
        printLink(head);
    }
}
