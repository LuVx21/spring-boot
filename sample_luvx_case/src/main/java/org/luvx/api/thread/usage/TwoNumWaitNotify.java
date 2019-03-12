package org.luvx.api.thread.usage;

/**
 * Function:两个线程交替执行打印 1~100 等待通知机制版
 * 取材于https://github.com/crossoverJie/ 为方便阅读略有修改
 */
public class TwoNumWaitNotify {
    public static void main(String[] args) {
        TwoNumWaitNotify twoNum = new TwoNumWaitNotify();

        Thread t1 = new Thread(new JiNum(twoNum), "t1");
        Thread t2 = new Thread(new OuNum(twoNum), "t2");

        t1.start();
        t2.start();
    }

    private int num = 1;

    // 输入奇数还是偶数的控制flag
    private volatile boolean flag = false;

    public static class OuNum implements Runnable {
        private TwoNumWaitNotify twoNum1;

        public OuNum(TwoNumWaitNotify twoNum) {
            this.twoNum1 = twoNum;
        }

        @Override
        public void run() {
            while (twoNum1.num <= 100) {
                synchronized (TwoNumWaitNotify.class) {
                    if (twoNum1.flag) {
                        System.out.println(Thread.currentThread().getName() + "+-+" + twoNum1.num);
                        twoNum1.num++;
                        twoNum1.flag = false;
                        TwoNumWaitNotify.class.notify();
                    } else {
                        try {
                            TwoNumWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static class JiNum implements Runnable {
        private TwoNumWaitNotify twoNum2;

        public JiNum(TwoNumWaitNotify twoNum) {
            this.twoNum2 = twoNum;
        }

        @Override
        public void run() {
            while (twoNum2.num <= 100) {
                synchronized (TwoNumWaitNotify.class) {
                    if (!twoNum2.flag) {
                        System.out.println(Thread.currentThread().getName() + "+-+" + twoNum2.num);
                        twoNum2.num++;
                        twoNum2.flag = true;
                        TwoNumWaitNotify.class.notify();
                    } else {
                        try {
                            TwoNumWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
