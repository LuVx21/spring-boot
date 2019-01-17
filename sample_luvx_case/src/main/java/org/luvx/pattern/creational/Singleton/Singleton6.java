package org.luvx.pattern.creational.Singleton;

import java.io.Serializable;

/**
 * 静态内部类实现单例模式
 * 同时避免反序列化破坏
 */
public class Singleton6 implements Serializable {

    private static Boolean inited = false;

    private Singleton6() {
        if (inited == Boolean.FALSE) {
            inited = !inited;
        } else {
            throw new RuntimeException("can not create second object!");
        }
    }

    private final static class SingletonHolder {
        private final static Singleton6 INSTANCE = new Singleton6();
    }

    public final static Singleton6 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Object readResolve() {
        return getInstance();
    }

}
