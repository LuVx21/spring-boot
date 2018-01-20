package org.luvx.api;

import org.junit.Test;

public class StringCase {

    /**
     * String较快的特殊情况
     */
    @Test
    public void run0() {
        // 没有字符串运算,本身就是"a b c"
        String s = "a " + "b " + "c";
        // 有字符串运算
        StringBuffer sb = new StringBuffer("a ").append("b ").append("c");
    }
}
