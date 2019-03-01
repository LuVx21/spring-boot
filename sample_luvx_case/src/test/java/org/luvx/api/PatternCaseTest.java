package org.luvx.api;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @ClassName: org.luvx.api
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/9 17:31
 */
public class PatternCaseTest {
    /**
     * 1~100的数字, 支持匹配类似023这样
     */
    private static final String  ID_REGEXP = "^(0){0,2}[1-9]$|^(0)?[1-9]\\d$|^100$";
    private static final Pattern ID_PATTERN;

    static {
        ID_PATTERN = Pattern.compile(ID_REGEXP);
    }

    // 劣
    public static void method0(String str) {
        for (int i = 0; i < 10000; i++) {
            Pattern.matches(ID_REGEXP, str);
            // 或
            // str.matches(ID_REGEXP);
        }
    }

    // 优
    public static void method1(String str) {
        for (int i = 0; i < 10000; i++) {
            ID_PATTERN.matcher(str).matches();
        }
    }

    // 劣
    public static void method2(String str) {
        for (int i = 0; i < 10000; i++) {
            str.replaceAll(ID_REGEXP, "LuVx");
        }
    }

    // 优
    public static void method3(String str) {
        for (int i = 0; i < 10000; i++) {
            ID_PATTERN.matcher(str).replaceAll("LuVx");
        }
    }

    @Test
    public void run00() {
        long start = System.currentTimeMillis();
        PatternCaseTest.method0("023");
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        PatternCaseTest.method1("023");
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void run01() {
        long start = System.currentTimeMillis();
        PatternCaseTest.method2("023152345579844321685746154023206548402304568574687023054687980");
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        PatternCaseTest.method3("023152345579844321685746154023206548402304568574687023054687980");
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
