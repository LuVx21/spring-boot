package org.luvx.api;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * @author renxie
 */
public class StringCase {

    /**
     * String较快的特殊情况
     */
    @Test
    public void stringTest() {
        // 没有字符串运算,本身就是"a b c"
        String s = "a " + "b " + "c";
        String ss = "a b c";
        String sss = new String("a b c");
        System.out.println(s == ss);// true
        System.out.println(ss == sss);// false

        // since jdk7
        String s0 = new StringBuilder("a ").append("b c").toString();
        String s1 = new StringBuilder("a ").append("b c1").toString();
        System.out.println(s0.intern() == s0);// false(常量池中存在),函数返回其实是s
        System.out.println(s1.intern() == s1);// true(常量池中不存在),jdk7之前为false
    }


    @Test
    public void stringTest1() {
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.toString().equals(""));
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

    @Test
    public void method() {
        String str = "from {0} to {1};";
        String result = MessageFormat.format(str, 10001 + "", 20000 + "");
        System.out.println(result);

    }

    @Test
    public void method1() {
        for (int i = 1; i < 5; i++) {
            System.out.print((i - 1) * 10000 + 1);
            System.out.print(":");
            System.out.println(i * 10000);
        }

    }
}
