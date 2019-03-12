package org.luvx.api.thread.threadlocal;

import java.util.ArrayList;
import java.util.List;

public class ThreadLocalDemo {

    public static ThreadLocal<List<String>> threadLocal = new ThreadLocal<>();

    public void setThreadLocal(List<String> values) {
        threadLocal.set(values);
    }

    public void getThreadLocal() {
        System.out.println(Thread.currentThread().getName());
        threadLocal.get().forEach(name -> System.out.println(name));
    }

    public static void main(String[] args) throws InterruptedException {

        final ThreadLocalDemo threadLocal = new ThreadLocalDemo();
        new Thread(() -> {
            List<String> params = new ArrayList<>(3);
            params.add("ren");
            params.add("xie");
            params.add("luvx");
            threadLocal.setThreadLocal(params);
            threadLocal.getThreadLocal();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                List<String> params = new ArrayList<>(2);
                params.add("Chinese");
                params.add("English");
                threadLocal.setThreadLocal(params);
                threadLocal.getThreadLocal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}