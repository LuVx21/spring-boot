package org.luvx.pattern.Singleton;

/**
 * 静态内部类实现单例模式
 * 同时避免反射破坏
 */
public class Singleton5 {

    private static Boolean inited = false;

    private Singleton5() {
        if (inited == Boolean.FALSE) {
            inited = !inited;
        } else {
            throw new RuntimeException("can not create second object!");
        }
    }

    private final static class SingletonHolder {
        private final static Singleton5 INSTANCE = new Singleton5();
    }


    public final static Singleton5 getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
