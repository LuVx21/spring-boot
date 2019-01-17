package org.luvx.pattern.structural.Proxy;

public class Aspect {

    /**
     * 打印前操作
     */
    public static void printBefore() {
        System.out.println("打印前操作...");
    }

    /**
     * 打印后操作
     */
    public static void printAfter() {
        System.out.println("打印后操作...");
    }
}
