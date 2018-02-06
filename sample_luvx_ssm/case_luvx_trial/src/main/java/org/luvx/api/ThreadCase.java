package org.luvx.api;

public class ThreadCase extends Thread {

    @Override
    public void run() {
        while (true) {
            System.out.println(this.currentThread().getName());
        }
    }

}
