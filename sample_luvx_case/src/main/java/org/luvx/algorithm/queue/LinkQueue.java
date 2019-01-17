package org.luvx.algorithm.queue;

/**
 * 队列:基于链表实现
 *
 * @param <E>
 */
public class LinkQueue<E> {
    // 结点类
    private class Node {
        private Object obj;
        private Node pre;

        Node(Object obj) {
            this.obj = obj;
        }
    }

    public LinkQueue() {
    }

    public LinkQueue(E e) {
    }

    /**
     *
     * @return
     */
    public boolean offer() {
        return true;
    }

    /**
     *
     * @return
     */
    public E peek() {
        return (E) new Object();
    }

    public E pool() {
        return (E) new Object();
    }
}
