package org.luvx.api;

import org.junit.Test;

/**
 * @author renxie
 */
public class SystemCaseTest {

    /**
     * 拷贝数组元素到另一个数组
     * Arrays.copyOf内部使用的即是这个
     */
    @Test
    public void arraycopyTest() {
        char[] oldChar = {'a', 'b', 'c'};
        char[] newChar = new char[]{'d', 'e', 'f', 'g'};
        System.arraycopy(oldChar, 1, newChar, 1, 2);
        System.out.println(newChar);
    }
}
