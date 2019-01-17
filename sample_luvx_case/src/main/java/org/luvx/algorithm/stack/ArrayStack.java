package org.luvx.algorithm.stack;

import java.util.Arrays;

/**
 * 栈:基于数组实现
 *
 * @param <E> 元素类型
 */
public class ArrayStack<E> {
    // 是否不定长
    private boolean isunbound = false;
    private Object[] objs;
    private int top = -1;

    public ArrayStack() {
        objs = new Object[8];
    }

    public ArrayStack(E e) throws Exception {
        push(e);
    }

    public ArrayStack(int i) throws Exception {
        if (i <= 0)
            throw new Exception("Illegal capacity" + i);
        else
            this.objs = new Object[i];
        isunbound = true;
    }

    /**
     * 入栈
     *
     * @return
     */
    public void push(E e) throws Exception {
        if (top == objs.length - 1) {
            if (!isunbound) {
                throw new Exception("is full");
            } else {
                grow();
            }
        } else
            objs[++top] = e;
    }

    /**
     * 出栈
     *
     * @return
     */
    public E pop() throws Exception {
        if (objs == null || objs.length == 0)
            throw new Exception("is empty");
        else
            return (E) objs[top--];
    }

    /**
     * 扩容
     */
    private void grow() {
        int length = objs.length;
        int growNum = length == 0 ? 1 : length / 2;
        objs = Arrays.copyOf(objs, length + growNum);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        System.out.println("----");
        for (int i = 0; i <= top; i++) {
            System.out.println(objs[top]);
        }
        return "----";
    }
}
