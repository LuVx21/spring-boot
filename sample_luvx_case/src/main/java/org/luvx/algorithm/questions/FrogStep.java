package org.luvx.algorithm.questions;

/**
 * 青蛙跳阶问题: 青蛙可以跳1阶，也可以跳2阶, N阶台阶有多少种跳的方式
 */
public class FrogStep {
    /**
     * 递归实现
     *
     * @param stepNums
     * @return
     */
    public static int frogStep(int stepNums) {
        if (stepNums < 1) {
            return 0;
        } else if (stepNums == 1 || stepNums == 2) {
            return stepNums;
        }

        return frogStep(stepNums - 1) + frogStep((stepNums - 2));
    }

    /**
     * 非递归实现
     *
     * @param stepNums
     * @return
     */
    public static int frogStep1(int stepNums) {
        if (stepNums == 1 || stepNums == 2) {
            return stepNums;
        }

        int sum = 0;
        int first = 1;
        int second = 2;

        for (int i = 3; i <= stepNums; i++) {
            sum = first + second;
            first = second;
            second = sum;
        }
        return sum;
    }

    public static int frogStep2(int stepNums) {
        if (stepNums == 1 || stepNums == 2) {
            return stepNums;
        }

        int num1 = 1;
        int num2 = 2;

        for (int i = 3; i <= stepNums; i++) {
            num2 = num1 + num2;
            num1 = num2 - num1;
        }
        return num2;
    }

    public static void main(String[] args) {
        System.out.println(frogStep(5));
        System.out.println(frogStep1(5));
        System.out.println(frogStep2(5));
    }

}
