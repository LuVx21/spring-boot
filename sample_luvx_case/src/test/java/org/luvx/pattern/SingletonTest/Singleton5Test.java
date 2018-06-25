package org.luvx.pattern.SingletonTest;

import org.luvx.pattern.Singleton.Singleton5;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Singleton5Test {

    public static void main(String[] args) throws Exception {
        run02();
    }

    /**
     * 即使定义是否实例化标志,仍然能创建多个实例
     *
     * @throws Exception
     */
    private static void run02() throws Exception {
        Singleton5 obj1 = Singleton5.getInstance();
        Field field = Singleton5.class.getDeclaredField("inited");
        field.setAccessible(true);
        field.set(null, Boolean.FALSE);

        Singleton5 obj2;
        Constructor<Singleton5> constructor = Singleton5.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        // 创建第2个实例,没有异常终了
        obj2 = constructor.newInstance();
        System.out.println(obj1);
        System.out.println(obj2);

    }

    private static void run01() throws Exception {
        Singleton5 obj1 = Singleton5.getInstance();
        Singleton5 obj2;
        Constructor<Singleton5> constructor = Singleton5.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        // 创建第2个实例,异常终了
        obj2 = constructor.newInstance();
    }
}
