package org.luvx.api;

public class RunnableCase implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("thread runnable!");
        }
    }
}
