package org.luvx.canal.simple;

import org.luvx.utils.CanalUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

/**
 * @ClassName: org.luvx.run
 * @Description: canal使用
 * @Author: Ren, Xie
 * @Date: 2019/1/21 19:19
 */
@Component
@Configuration
@EnableScheduling
public class MulThreadCanalClient {

    @Value("${canal.ip}")
    private String ip;
    @Value("${canal.port}")
    private int    port;
    @Value("${canal.destination}")
    private String destinations;
    @Value("${canal.username}")
    private String username;
    @Value("${canal.password}")
    private String password;
    @Value("${canal.filter}")
    private String filter;

    private        String[]        destinationsArray = null;
    private static TreeSet<String> activeThreadSet   = new TreeSet<>();

    @PostConstruct
    public void init() {
        destinationsArray = destinations.split(",");
    }

    @Scheduled(cron = "0 0/1 0-23 * * ?")
    // @Scheduled(fixedRate = 30 * 1000)
    public void execute() {
        ExecutorService service = new ThreadPoolExecutor(8, 8, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        for (int i = 0; i < destinationsArray.length; i++) {
            String destination = destinationsArray[i];

            if (!activeThreadSet.contains(destination)) {
                FutureTask<String> task = new FutureTask<>(new GetBinlogThread(destination));
                service.execute(task);
                activeThreadSet.add(destination);
            }
        }
        service.shutdown();

    }

    class GetBinlogThread implements Callable<String> {
        private String destination;

        GetBinlogThread(String destination) {
            this.destination = destination;
        }

        @Override
        public String call() {
            CanalUtils.getBinlog(ip, port, destination);
            activeThreadSet.remove(destination);
            return destination;
        }
    }
}