package org.luvx.algorithm.stack;

import org.junit.Test;


public class ArrayStackTest {

    @Test
    public void push() throws Exception {

        ArrayStack<String> stack = new ArrayStack<String>();
        stack.push("a");
        stack.push("b");
        System.out.println(stack);
        stack.pop();
        System.out.println(stack);
    }
}