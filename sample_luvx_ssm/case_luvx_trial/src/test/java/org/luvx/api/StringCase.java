package org.luvx.api;

import org.junit.Test;

/**
 * @author renxie
 */
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

    /**
     * StringBuffer
     * 线程安全,方法有synchronized
     */
    @Test
    public void StringBufferTest() {
        // 初始容量为16的char型数组,同时计数已使用了的位置数量
        StringBuffer sb = new StringBuffer();
        // 扩容检查,变为原来的2倍+2
        sb.append("ab");
        // 下标检查->容量确认
        sb.replace(1, 2, "c");
        // 下标检查->返回值
        sb.charAt(1);
        // 下标检查->删除长度>0进行删除
        sb.delete(1, 2);
    }


    /**
     * StringBuilder
     * 线程不安全,方法无synchronized
     * 基本同StringBuffer,方法内部都是调用
     * AbstractStringBuilder的实现
     */
    @Test
    public void StringBuilderTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("ab");
        sb.replace(1, 2, "c");
        sb.charAt(1);
        sb.delete(1, 2);
    }

    /**
     * `==`,equals(),hashCode()
     */
    @Test
    public void equalsTest() {

        String aa = "aa";
        String cc = "aa";
        // true
        System.out.println(aa == cc);
        // true
        System.out.println(aa.equals(cc));
        // true
        System.out.println(aa.hashCode() == cc.hashCode());


        aa = new String("aa");
        cc = new String("aa");
        // false
        System.out.println(aa == cc);
        System.out.println(aa.equals(cc));
        System.out.println(aa.hashCode() == cc.hashCode());
    }

}
