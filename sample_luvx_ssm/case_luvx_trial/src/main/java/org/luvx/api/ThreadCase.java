package org.luvx.api;

/**
 * 继承Thread方式创建线程
 */
public class ThreadCase extends Thread {

    @Override
    public void run() {
        while (true) {
            System.out.println(this.currentThread().getName());
        }
    }

}
