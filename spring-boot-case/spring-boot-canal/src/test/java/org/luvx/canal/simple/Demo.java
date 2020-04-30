package org.luvx.canal.simple;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionEnd;
import com.alibaba.otter.canal.protocol.Message;
import org.junit.Test;
import org.luvx.utils.CanalUtils;

import java.net.InetSocketAddress;
import java.util.List;


/**
 * @ClassName: org.luvx.canal
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/5 11:56
 */
public class Demo {

    @Test
    public void DemoTest() throws Exception {
        CanalConnector connector = CanalUtils.getConnect(AddressUtils.getHostIp(), 11111, "example");

        try {
            connector.connect();
            // connector.subscribe("boot\\..*");
            connector.subscribe("");
            connector.rollback();
            Message message = connector.getWithoutAck(1024);
            long batchId = message.getId();
            int size = message.getEntries().size();

            if (batchId == -1 || size == 0) {
                System.out.println("没有获取到新的binlog!");
            } else {
                List<CanalEntry.Entry> entrys = message.getEntries();
                CanalUtils.analysisBinlog(entrys);
            }
            connector.ack(batchId);
        } finally {
            connector.disconnect();
        }
    }
}
