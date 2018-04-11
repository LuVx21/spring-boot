package org.luvx.api.reflect;

import org.junit.Test;

import java.lang.reflect.Field;

public class Reflect {

    @Test
    public void run01() throws Exception {
        Class cache = Integer.class.getDeclaredClasses()[0]; // 1
        Field myCache = cache.getDeclaredField("cache"); // 2
        myCache.setAccessible(true);// 3

        Integer[] newCache = (Integer[]) myCache.get(cache); // 4
        newCache[132] = newCache[133]; // 5

        int a = 2;
        int b = a + a;
        System.out.println(b);
        System.out.printf("%d + %d = %d", a, a, b);
    }


}