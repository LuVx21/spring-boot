package org.luvx.api.thread.future;

import org.luvx.api.thread.entity.CallableCase;
import org.luvx.api.thread.entity.RunnableCase;
import org.luvx.api.thread.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureCase {

    public static void main(String[] args) throws Exception {
        ExecutorService service = ThreadUtils.getThreadPool();

        List<FutureTask<String>> list = new ArrayList<>(5);

        for (int i = 0; i < 6; i++) {
            int index = i;
            FutureTask<String> task = new FutureTask<>(new CallableCase("1234" + index));
            service.execute(task);
            list.add(task);
        }

        service.shutdown();

        while (!service.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("线程未结束...");
        }

        System.out.println("-------------------------------");

        for (FutureTask<String> task : list) {
            if (task.isDone()) {
                String result = task.get();
                System.out.println(result);
            } else {
                System.out.println("task未结束");
                continue;
            }
        }
    }
}