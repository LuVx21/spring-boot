package org.luvx.api.thread;

import java.util.concurrent.TimeUnit;

/**
演示volatile的效果
 */

public class Counter {
    private static  boolean stop ;
    //private static volatile boolean stop ;
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stop) {
                    i++;
                }
            }
        } );
        t.start();

        TimeUnit.MILLISECONDS .sleep(5);
        stop = true;
    }
}
