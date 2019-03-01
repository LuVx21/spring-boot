package org.luvx.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionEnd;
import com.alibaba.otter.canal.protocol.Message;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @ClassName: org.luvx.canal
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/2/13 17:30
 */
@Component
public class Demo {
    public static void main(String[] args) throws Exception {
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(AddressUtils.getHostIp(), 11111),
                // new InetSocketAddress("10.0.0.46", 11111),
                "example",
                "",
                "");

        try {
            connector.connect();
            connector.subscribe("boot\\..*");
            connector.rollback();
            Message message = connector.getWithoutAck(1024);
            long batchId = message.getId();
            int size = message.getEntries().size();
            System.out.println(batchId + ":" + size);
            if (batchId == -1 || size == 0) {
                System.out.println("没有获取到新的binlog!");
            } else {
                List<CanalEntry.Entry> entrys = message.getEntries();
                for (CanalEntry.Entry entry : entrys) {

                    // RowData rowData = RowData.parseFrom(entry.getStoreValue());
                    TransactionEnd txEnd = TransactionEnd.parseFrom(entry.getStoreValue());
                    RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
                    System.out.println(entry.getHeader().getSchemaName());
                    String txId = txEnd.getTransactionId();
                    System.out.println(txId + "\t" + entry.getEntryType() + "\t" + rowChange.getEventType());
                    // if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN) {
                    //     long time = entry.getHeader().getExecuteTime();
                    //     String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
                    //     System.out.println("事务开始时间: " + str);
                    // } else if (entry.getEntryType() == EntryType.TRANSACTIONEND) {
                    //     long time = entry.getHeader().getExecuteTime();
                    //     String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
                    //     System.out.println("事务结束时间: " + str);
                    // }
                }
            }
            connector.ack(batchId);
        } finally {
            connector.disconnect();
        }
    }
}
