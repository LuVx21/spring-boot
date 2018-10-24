package org.luvx.api;

import java.math.BigDecimal;

/**
 * 1. 类型精度
 * 2. 类型传递
 */
public class TypeCase {
    public static void main(String[] args) {
        int i = 100;
        System.out.print(i + " ");
        changeInt(i);
        System.out.println(i);

        // String str = "aaa";
        String str = new String("aaa");
        System.out.print(str);
        changeStr(str);// new与否都不变化
        System.out.println(" " + str);

        User point = new User(1, 2);
        System.out.print("id=" + point.id + ";age=" + point.age);
        changePoint(point);// 会变化
        System.out.println(" id=" + point.id + ";age=" + point.age);
    }

    public static void method0() {
        System.out.println(2.0 - 1.9);
        System.out.println((new BigDecimal("2.0")).subtract(new BigDecimal("1.9")).doubleValue());
    }

    // 值传递:不改变
    public static void changeInt(int i) {
        i = 1024;
    }

    // 引用传递:不改变
    // java在方法传递参数时，是将变量复制一份，然后传入方法体去执行。
    // String类的存储数组value也是final的
    public static void changeStr(String str) {
        str = "bbb";
    }

    // 引用传递:改变了引用类型的属性
    public static void changePoint(User point) {
        point.id = 3;
        point.age = 4;
    }

    static class User {
        int id;
        int age;

        public User(int id, int age) {
            this.id = id;
            this.age = age;
        }
    }
}
