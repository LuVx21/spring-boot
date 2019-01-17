package org.luvx.algorithm.linkarray;

/**
 * 链表:
 * 情形1:
 * 1->1->2->3->4->5
 * ↓
 * 1->1->3->5->2->4
 * 情形2:
 * 2->1->2->3->4->5
 * ↓
 * 1->3->5->2->2->4
 */
public class ModifiedLink {

    // public static Node modifiedLink0(Node head) {
    //     if (head == null || head.next == null)
    //         return head;
    //     boolean flag = false;
    //     Node current = head;
    //     while (current != null) {
    //         if (!flag && (current.data % 2 != 0)) {
    //             head = current;
    //             flag = true;
    //         }
    //
    //
    //         current = current.next;
    //     }
    //
    //
    //     if (head == null || head.next == null)
    //         return head;
    // }

    public static Node modifiedLink(Node head) {
        if (head == null || head.next == null)
            return head;
        Node _head = head;
        Node current = head;
        Node before = head;

        while (current != null) {
            if (current.data % 2 != 0) {
                head = current;
                break;
            }
            current = current.next;
        }

        current = _head;
        Node last = head;
        while (current != null) {
            if (current.data % 2 != 0 && current != head) {
                current.next = last.next;
                last.next = current;

            }
        }

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
        Node head = modifiedLink(node1);
        LinkUtils.printLink(head);
    }
}
