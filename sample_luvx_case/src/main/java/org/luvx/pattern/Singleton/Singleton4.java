package org.luvx.pattern.Singleton;

public class Singleton4 {
    /**
     * 饿汉式
     * 静态代码块形式的饿汉式
     * 线程安全
     */
    private static Singleton4 instance = null;

    static {
        instance = new Singleton4();
    }


    private Singleton4() {

    }

    public static Singleton4 getInstance() {
        return instance;
    }
}
