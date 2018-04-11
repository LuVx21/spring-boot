package org.luvx.pattern.Singleton;

public class Singleton2 {

    /**
     * 静态内部类实现单例模式
     * 实现延迟加载
     * 保证线程安全
     * 不影响系统性能
     */
    private Singleton2() {
    }

    private static class SingletonHolder {
        private static final Singleton2 INSTANCE = new Singleton2();
    }


    public static final Singleton2 getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
