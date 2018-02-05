package org.luvx.algorithm;

/**
 * 栈:基于链表实现
 *
 * @param <E> 元素类型
 */
public class LinkStack<E> {

    // 结点类
    private class Node {
        private Object obj;
        private Node pre;

        Node(Object obj) {
            this.obj = obj;
        }
    }

    // 栈的大小
    private int size = 0;
    private Node top = null;

    public LinkStack() {
    }

    public LinkStack(E e) {
        push(e);
    }

    /**
     * 入栈
     */
    public void push(E e) {
        Node node = new Node(e);
        node.pre = top;
        top = node;
        size++;
    }

    /**
     * 出栈
     *
     * @return 栈顶元素
     */
    public E pop() throws Exception {
        if (size == 0)
            throw new Exception("is Enpty");
        E e = (E) top.obj;
        top = top.pre;
        size--;
        return e;
    }

    /**
     * @return 栈的大小
     */
    public int getSize() {
        return size;
    }

    /**
     * 遍历栈
     *
     * @return 栈内容
     */
    @Override
    public String toString() {
        System.out.println("----");
        Node node = top;
        while (node != null) {
            System.out.println(node.obj);
            node = node.pre;
        }

        return "----";
    }
}
