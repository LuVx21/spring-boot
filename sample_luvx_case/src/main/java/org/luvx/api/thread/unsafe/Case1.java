package org.luvx.api.thread.unsafe;

import com.google.common.collect.Sets;
import org.luvx.api.thread.utils.ThreadUtils;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: org.luvx.api.thread.unsafe
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/26 10:58
 */
public class Case1 {
    private volatile static Set<String> activeThreadSet = new TreeSet<>();
    // private volatile static Set<String> activeThreadSet = Sets.newConcurrentHashSet();

    public static void main(String[] args) {
        ExecutorService service = ThreadUtils.getThreadPool();

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 5; i++) {
                int a = i;
                service.execute(() -> {
                    String name = "name:" + a;
                    activeThreadSet.add(name);
                    System.out.println(Thread.currentThread().getName() + ":" + activeThreadSet);
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    activeThreadSet.remove(name);
                });
            }
        }

        service.shutdown();
        try {
            while (!service.awaitTermination(2, TimeUnit.SECONDS)) {
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("剩余:" + activeThreadSet);
    }
}
