package org.luvx.api.thread.entity;

/**
 * @ClassName: org.luvx.api.thread.thread
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 14:18
 */
public class Task {

    public static void execute(String name) {
        try {
            System.out.println("正在处理: " + name);
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
