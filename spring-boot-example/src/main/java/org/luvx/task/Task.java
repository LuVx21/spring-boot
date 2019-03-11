package org.luvx.task;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {
    @Async
    @Scheduled(cron = "0 0 * * * ?")
    public void execute() {
        System.out.println("1" + System.currentTimeMillis());
    }

    @Async
    @Scheduled(cron = "0 30 * * * ?")
    public void execute1() {
        System.out.println("2" + System.currentTimeMillis());
    }
}
