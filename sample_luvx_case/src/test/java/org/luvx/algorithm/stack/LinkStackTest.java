package org.luvx.algorithm.stack;

import org.junit.Test;


public class LinkStackTest {

    @Test
    public void push() throws Exception {

        LinkStack<String> stack = new LinkStack<String>();
        stack.push("a");
        stack.push("b");
        System.out.println(stack.getSize());
        System.out.println(stack);
        String str = stack.pop();
        System.out.println(str);

    }
}