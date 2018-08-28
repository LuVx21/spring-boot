package org.luvx.api;

import java.util.Random;

/**
 * 获取随机数的方式
 */
public class RandomNum {

    public static int getRandom(int start, int end) {
        // [0.0 , 1.0)
        double num = Math.random();
        int bound = end - start + 1;
        return (int) (num * bound) + start;
    }

    public static int getRandom1(int start, int end) {
        Random random = new Random();
        int bound = end - start + 1;
        return random.nextInt(bound) + start;
    }

}
