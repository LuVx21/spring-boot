package org.luvx.api.Java8.Lambda;

import org.junit.Test;
import org.luvx.api.Java8.StaticReference.Convertable;

/**
 * Lambda的范围
 */
public class Attri {

    static int outerStaticNum = 0;
    int outerNum = 0;

    @Test
    public void testScopes() {
        int aa = 233;

        // 访问局部变量
        Convertable<Integer, String> stringConverter0 = (from) -> String.valueOf(from + aa);

        // 访问成员普通变量
        Convertable<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        // 访问成员静态变量
        Convertable<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };

        System.out.println(stringConverter0.convert(1));
        stringConverter1.convert(23);
        stringConverter2.convert(24);
        System.out.println(outerNum);
        System.out.println(outerStaticNum);
    }
}
