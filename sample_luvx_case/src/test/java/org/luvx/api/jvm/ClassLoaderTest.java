package org.luvx.api.jvm;

import org.junit.Test;

public class ClassLoaderTest {

    /**
     * 获取类加载器的方式
     */
    @Test
    public void run01() {
        Class clazz = ClassLoaderTest.class;
        ClassLoader myclassloader = clazz.getClassLoader();
        System.out.println(myclassloader);
    }


}