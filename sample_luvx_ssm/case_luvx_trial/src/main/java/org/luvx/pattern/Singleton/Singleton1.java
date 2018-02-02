package org.luvx.pattern.Singleton;

public class Singleton1 {
    /**
     * 饿汉式
     * 不管那么多先创建一个对象,
     * 之后不再提供可以new的途径
     * 但会始终占用内存
     */
    private static final Singleton1 instance = new Singleton1();

    private Singleton1() {

    }

    public static Singleton1 getInstance() {
        return instance;
    }
}
