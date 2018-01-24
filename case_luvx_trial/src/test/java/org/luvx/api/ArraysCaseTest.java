package org.luvx.api;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author renxie
 */
public class ArraysCaseTest {

    /**
     *根据现有数据拷贝出一个数组
     * 新的比旧的大,则补充空格
     */
    @Test
    public void copyOfTest() {
        char[] oldChar = {'a', 'b', 'c'};
        // new一个给定长度的数组->调用System.arraycopy移动数据
        char[] newChar = Arrays.copyOf(oldChar, 2);
        System.out.println(newChar);
    }
}
