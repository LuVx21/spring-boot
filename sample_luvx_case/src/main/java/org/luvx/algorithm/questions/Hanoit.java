package org.luvx.algorithm.questions;

/**
 * 汉诺塔问题
 */
public class Hanoit {
    /**
     * @param n           盘子的数目
     * @param origin      源座
     * @param assist      辅助座
     * @param destination 目的座
     */
    public static void hanoit(int n, char origin, char assist, char destination) {
        if (n == 1) {
            move(origin, destination);
        } else {
            // 先经目的座移动到辅助座
            hanoit(n - 1, origin, destination, assist);
            move(origin, destination);
            // 从辅助座移动到目的座
            hanoit(n - 1, assist, origin, destination);
        }
    }

    private static void move(char origin, char destination) {
        System.out.println("Direction:" + origin + "--->" + destination);
    }
}
