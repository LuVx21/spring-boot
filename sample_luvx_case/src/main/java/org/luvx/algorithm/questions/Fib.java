package org.luvx.algorithm.questions;

/**
 * 斐波那契数列:第n个数据的值
 * 用int也不好
 */
public class Fib {
    /**
     * 递归方式:有性能问题
     *
     * @param n
     * @return
     */
    public static int fib1(int n) {
        if (n == 1 || n == 2)
            return 1;

        return fib1(n - 1) + fib1(n - 2);
    }

    public static int fib2(int n) {
        if (n == 1 || n == 2)
            return 1;
        int num1 = 1;
        int num2 = 1;
        int sum = 0;

        for (int i = 3; i <= n; i++) {
            sum = num1 + num2;
            num1 = num2;
            num2 = sum;
        }

        return sum;
    }

    /**
     * 循环方式
     *
     * @param n
     * @return
     */
    public static int fib3(int n) {
        if (n == 1 || n == 2)
            return 1;
        int num1 = 1;
        int num2 = 1;

        for (int i = 3; i <= n; i++) {
            num2 = num1 + num2;
            num1 = num2 - num1;
        }
        return num2;
    }

    public static void main(String[] args) {
        // 1 1 2 3 5 8 13 21 34 55
        System.out.println(fib1(10));
        System.out.println(fib2(10));
        System.out.println(fib3(10));
    }
}
