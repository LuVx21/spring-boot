package org.luvx.canal.simple;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import org.junit.Test;
import org.luvx.utils.CanalUtils;
import org.luvx.utils.TimeUtils;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * @ClassName: org.luvx.canal
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/5 11:56
 */
public class BinlogTimePositionTest {

    @Test
    public void DemoTest() {

        CanalConnector connector = CanalUtils.getConnect(AddressUtils.getHostIp(), 11111, "pa");

        int emptyCount = 0;

        try {
            connector.connect();
            // connector.subscribe("boot\\..*");
            connector.subscribe("");
            connector.rollback();

            // int totalEmptyCount = 60;
            // while (emptyCount < totalEmptyCount) {
                // outter:
                while (true) {
                System.out.println("-------------------------------------");
                Message message = connector.getWithoutAck(4096 * 8);
                long batchId = message.getId();
                int size = message.getEntries().size();

                System.out.println(batchId + ":" + size);

                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    // System.out.println("empty count : " + emptyCount);
                } else {
                    List<CanalEntry.Entry> entrys = message.getEntries();
                    for (CanalEntry.Entry entry : entrys) {

                        // String tableName = entry.getHeader().getTableName();
                        // // System.out.println(tableName);
                        // if (!tableName.equals("user_renxie2")) {
                        //     continue;
                        // }

                        long position = entry.getHeader().getLogfileOffset();
                        long time = entry.getHeader().getExecuteTime();
                        String type = entry.getEntryType().toString();

                        String timeTxStart = TimeUtils.formatter.format(time);
                        System.out.println("位置: " + position + " 事件时间:" + timeTxStart + " 事件类型: " + type);

                        // if ((time >= 1552492800000L && time < 1552579200000L)) {
                        //     String timeTxStart = TimeUtils.formatter.format(time);
                        //     System.out.println("位置: " + position + " 事件类型: " + type + "  事件时间:" + timeTxStart);
                        //     // break outter;
                        // } else {
                        // }
                    }
                }
                // connector.ack(batchId);

                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            connector.disconnect();
        }
    }
}
