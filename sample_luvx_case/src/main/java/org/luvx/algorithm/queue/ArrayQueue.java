package org.luvx.algorithm.queue;

import java.util.Arrays;

/**
 * 队列:基于数组实现(不定长)
 *
 * @param <E>
 */
public class ArrayQueue<E> {

    private E[] elements;
    private int size = 0;

    public ArrayQueue() {
        elements = (E[]) new Object[8];
    }

    public ArrayQueue(E e) {
        elements = (E[]) new Object[8];
        elements[0] = e;
        size++;
    }

    /**
     * @return
     */
    public boolean offer(E e) {
        if (size > elements.length - 1) {
            grow();
        }
        elements[size++] = e;
        return true;
    }

    /**
     * 查看队列头元素
     *
     * @return
     */
    public E peek() {
        return elements[0];
    }

    /**
     * 出队列
     *
     * @return
     */
    public E poll() {
        E result = elements[0];

        for (int i = 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }
        size--;
        return result;
    }

    /**
     * 扩容:原来的1.5
     */
    private void grow() {
        int length = elements.length;
        int growNum = (length == 0) ? 1 : length / 2;
        elements = Arrays.copyOf(elements, length + growNum);
    }

    @Override
    public String toString() {
        System.out.println("----");
        for (int i = 0; i < size; i++) {
            System.out.println(elements[i]);
        }
        return "----";
    }
}
