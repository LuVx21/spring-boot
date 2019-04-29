package org.luvx.qa;

/**
 * @ClassName: org.luvx.qa
 * @Description: 父子类属性名相同
 * @Author: Ren, Xie
 * @Date: 2019/4/26 13:48
 */
public class Q1 {
    private static class Parent {
        private int i = 10;
    }

    private static class Sub extends Parent {
        private int i = 100;
    }

    public static void main(String[] args) {
        Parent p = new Sub();
        Sub s = new Sub();

        // 110
        System.out.println((p.i + s.i));
    }
}

