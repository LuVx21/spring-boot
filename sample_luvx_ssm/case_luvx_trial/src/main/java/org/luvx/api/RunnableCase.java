package org.luvx.api;

/**
 * 实现Runnable实现线程
 */
public class RunnableCase implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("thread runnable!");
        }
    }
}
