package org.luvx.api.thread;

public class TwoNumNonBlock implements Runnable {
    /**
     * 当flag为1时只有奇数线程可以执行，并将其置为0
     * 当flag为0时只有偶数线程可以执行，并将其置为1
     */
    private volatile static int flag = 1;
    private int start;
    private int end;
    private String name;

    private TwoNumNonBlock(int start, int end, String name) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        while (start <= end) {
            int f = flag;
            if ((start & 0x01) == f) {
                System.out.println(name + "+-+" + start);
                start += 2;
                flag ^= 0x1;
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new TwoNumNonBlock(1, 100, "t1")).start();
        new Thread(new TwoNumNonBlock(2, 100, "t2")).start();
    }
}