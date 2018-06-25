package org.luvx.api;

/**
 * 静态与内部类
 */
public class StaticCase {

    private String unStaticFiled = "Static Filed";
    private static String staticFiled = "static Filed";

    public class Apple {
        private String unStaticApple = "unStatic Apple";
        // 普通内部类内不可以定义静态属性
        // private static String Apple = "Apple";

        public void unStaticfun(String str) {
            // 普通内部类内使用外部类非静态属性
            System.out.println(unStaticFiled);
            // 普通内部类内使用外部类非静态方法
            unStaticFun(str);
            // 普通内部类内使用外部静态属性
            // System.out.println(staticFiled);
            // 普通内部类内使用外部静态方法
            // staticFun(str);
        }

        // 普通内部类内不可以定义静态方法
        // private static void staticfun() {
        // }
    }

    public static class Peach {
        private String unStaticPeach = "unStatic Peach";
        // 静态内部类内可以定义静态属性
        private static String staticPeach = "static Peach";

        public void unStaticfun(String str) {
            // 静态内部类内不能使用外部类非静态属性
            // System.out.println(unStaticFiled);
            // 静态内部类内使用外部类非静态方法
            // unStaticFun(str);
            // 静态内部类内使用外部静态属性
            System.out.println(staticFiled);
            // 静态内部类内使用外部静态方法
            staticFun(str);
        }

        // 静态内部类内可以定义静态方法
        public static void staticfun(String str) {
            // 静态内部类内不能使用外部类非静态属性
            // System.out.println(unStaticFiled);
            // 静态内部类内使用外部类非静态方法
            // unStaticFun(str);
            // 静态内部类内使用外部静态属性
            System.out.println(staticFiled);
            // 静态内部类内使用外部静态方法
            staticFun(str);
        }

    }

    public void unStaticFun(String str) {
        System.out.println("this is unstatic method......" + str);

        // 局部内部类
        class Bear {
        }
    }

    public static void staticFun(String str) {
        System.out.println("this is static method......" + str);
    }

    public static void main(String[] args) {
        String str = "foo";
        StaticCase obj = new StaticCase();
        // ----------------------------------
        StaticCase.Apple apple = obj.new Apple();
        System.out.println(apple.unStaticApple);
        // 普通内部类的非静态方法
        apple.unStaticfun(str);
        // ----------------------------------
        StaticCase.Peach peach = new StaticCase.Peach();
        System.out.println(peach.unStaticPeach);
        // 静态内部类的非静态方法
        peach.unStaticfun(str);
        // 静态内部类的静态方法
        StaticCase.Peach.staticfun(str);
    }
}
