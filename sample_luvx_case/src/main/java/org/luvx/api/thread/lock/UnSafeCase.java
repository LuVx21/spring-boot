package org.luvx.api.thread.lock;

import org.luvx.api.thread.utils.ThreadUtils;

import java.util.concurrent.ExecutorService;

/**
 * @ClassName: org.luvx.api.thread.lock
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 20:28
 */
public class UnSafeCase {

    private static int a = 0;

    public static void main(String[] args) {
        ExecutorService service = ThreadUtils.getThreadPool();
        for (int i = 0; i < 10; i++) {
            service.execute(() -> a++);
            System.out.println(a);
        }
        service.shutdown();
    }
}
