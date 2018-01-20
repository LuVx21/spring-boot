package org.luvx.pattern.Singleton;

public class Singleton {
    private static Singleton INSTANCE;

    /**
     * 构造函数为private
     */
    private Singleton() {
    }

    /**
     * 方式1
     * 懒汉式写法,
     * 需要的时候在创建对象
     * 多线程下不适用,2,3,4等为解决多线程问题
     * 性能也会手影响
     *
     * @return
     */
    public static Singleton getInstance1() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    /**
     * 方式2
     * 懒汉式+synchronized
     * 增加了线程锁的判断和获取
     * 高并发多线程环境下效率差
     *
     * @return
     */
    public static synchronized Singleton getInstance2() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    /**
     * 方式3
     * 懒汉式+synchronized
     * 在2的基础上只对创建对象过程加锁
     * 这种情况下仍可能出现多实例情况
     *
     * @return
     */
    public static Singleton getInstance3() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * 方式4
     * 为解决3可能出现的问题,
     * 使用双重校验锁,但实例对象需要被volatile修饰
     *
     * @return
     */
    public static Singleton getInstance4() {
        // private volatile static Singleton INSTANCE;
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 方式5
     */
    public static Singleton getInstance5() {
        // private stacin final Singleton INSTANCE;
        return INSTANCE;
    }
}
