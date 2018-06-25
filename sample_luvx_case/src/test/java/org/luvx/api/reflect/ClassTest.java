package org.luvx.api.reflect;

import org.junit.Test;

import java.lang.reflect.Modifier;

public class ClassTest {
    /**
     * 获取类对象的方式
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void run00() throws ClassNotFoundException {
        // 方式1
        // InnerClass inner = new InnerClass();
        // Class clazz = inner.getClass();

        // 方式2
        // Class clazz = Class.forName("org.luvx.reflect.InnerClass");

        // 方式3
        Class clazz = InnerClass.class;

        System.out.println(clazz);

    }


    @Test
    public void run01() throws Exception {
        Class[] clazz = InnerClass.class.getDeclaredClasses();
        for (Class aclazz : clazz) {

            System.out.println(aclazz.getCanonicalName());
            // System.out.println(aclazz.getModifiers());
            System.out.println(Modifier.toString(aclazz.getModifiers()));

        }
    }
}
