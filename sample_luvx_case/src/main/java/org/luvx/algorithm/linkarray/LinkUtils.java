package org.luvx.algorithm.linkarray;

public class LinkUtils {
    /**
     * 遍历链表
     *
     * @param head
     */
    public static void printLink(Node head) {
        while (head != null) {
            System.out.print(head.date + "->");
            head = head.next;
        }
        System.out.println("\n--------------");
    }
}
