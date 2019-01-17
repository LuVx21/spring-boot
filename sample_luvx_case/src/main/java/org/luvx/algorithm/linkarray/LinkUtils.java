package org.luvx.algorithm.linkarray;

public class LinkUtils {
    /**
     * 遍历链表
     *
     * @param head
     */
    public static void printLink(Node head) {
        while (head != null) {
            System.out.print(head.data + "->");
            head = head.next;
        }
        System.out.println("\n--------------");
    }

    /**
     * 修改节点的下一个指向:from -> to
     *
     * @param from
     * @param to
     */
    public static void changeNext(Node from, Node to) {
        from.next = to;
    }

    /**
     * 修改节点的上一个指向:from -> to
     *
     * @param from
     * @param to
     */
    public static void changepre(Node from, Node to) {
        from.pre = to;
    }

}
