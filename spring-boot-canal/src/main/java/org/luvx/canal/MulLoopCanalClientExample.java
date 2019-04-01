package org.luvx.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import org.luvx.utils.CanalUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: org.luvx.run
 * @Description: canal使用
 * @Author: Ren, Xie
 * @Date: 2019/1/21 19:19
 */
@Component
@EnableScheduling
public class MulLoopCanalClientExample {

    @Value("${canal.ip}")
    private String ip;
    @Value("${canal.port}")
    private int    port;
    @Value("${canal.destination}")
    private String destination;
    @Value("${canal.username}")
    private String username;
    @Value("${canal.password}")
    private String password;
    @Value("${canal.filter}")
    private String filter;

    private List<CanalConnector> connectors = new ArrayList<>();

    // @PostConstruct
    // private void init() {
    //     ip = "169.254.186.8";
    //     port = 11111;
    //     username = "";
    //     password = "";
    //     destination = "example,example1";
    //     filter = "";
    // }

    private void initConnectors() {
        connectors.clear();
        String[] destinations = destination.split(",");
        for (int i = 0; i < destinations.length; i++) {
            CanalConnector connector = CanalConnectors.newSingleConnector(
                    new InetSocketAddress(ip, port),
                    destinations[i],
                    username,
                    password);
            connectors.add(connector);
        }
    }

    // @Scheduled(fixedRate = 70 * 1000)
    public void getBinlog() {
        initConnectors();

        int batchSize = 1024;
        int count = 60;

        while (count >= 0) {
            for (int i = 0; i < connectors.size(); i++) {
                CanalConnector connector = connectors.get(i);
                try {
                    connector.connect();
                    connector.subscribe("");
                    connector.rollback();

                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        continue;
                    } else {
                        count = 60;
                        CanalUtils.analysisBinlog(message.getEntries());
                    }

                    /// 提交确认
                    connector.ack(batchId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // connector.disconnect();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("empty count : " + count);
            count--;
        }

        for (int i = 0; i < connectors.size(); i++) {
            connectors.get(i).disconnect();
        }
    }

    public static void main(String[] args) {
        MulLoopCanalClientExample example = new MulLoopCanalClientExample();
        example.getBinlog();
    }

}