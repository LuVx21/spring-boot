package org.luvx.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import org.luvx.utils.CanalUtils;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @ClassName: org.luvx.run
 * @Description: canal使用
 * @Author: Ren, Xie
 * @Date: 2019/1/21 19:19
 */
@Component
public class SimpleCanalClientExample {

    private static boolean isRemote = false;

    private static final String ip;
    private static final int    port        = 11111;
    private static final String destination = "example1";
    private static final String username    = "";
    private static final String password    = "";
    private static final String filter;

    static {
        if (isRemote) {
            ip = "10.0.0.46";
            filter = "pa.t_policy_change";
        } else {
            ip = "169.254.186.8";
            filter = "boot.user_renxie2";
        }
    }

    public static void main(String[] args) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(ip, port),
                destination,
                username,
                password);

        int batchSize = 1000;
        int emptyCount = 0;

        try {
            connector.connect();
            connector.subscribe("");
            connector.rollback();

            int totalEmptyCount = 120;
            while (emptyCount < totalEmptyCount) {
                /// 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    emptyCount = 0;
                    /// System.out.printf("message[batchId=%s, size=%s] \n", batchId, size);
                    CanalUtils.analysisBinlog(message.getEntries());
                }

                /// 提交确认
                connector.ack(batchId);

                /// 处理失败, 回滚数据
                // connector.rollback(batchId);
            }

            System.out.println("empty too many times, exit");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
    }


}