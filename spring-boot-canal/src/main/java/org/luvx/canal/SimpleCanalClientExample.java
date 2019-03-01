package org.luvx.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

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
    private static final String destination = "example";
    private static final String username    = "";
    private static final String password    = "";
    private static final String filter;

    static {
        if (isRemote) {
            ip = "10.0.0.46";
            filter = "pa.t_policy_change";
        } else {
            ip = "169.254.186.8";
            filter = "boot.user1";
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
            connector.subscribe(filter);
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
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    emptyCount = 0;
                    /// System.out.printf("message[batchId=%s, size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                /// 提交确认
                connector.ack(batchId);

                /// 处理失败, 回滚数据
                // connector.rollback(batchId);
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN) {
                long time = entry.getHeader().getExecuteTime();
                String timeTxStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
                System.out.println("事务开始时间: " + timeTxStart);
                continue;
            } else if (entry.getEntryType() == EntryType.TRANSACTIONEND) {
                long time = entry.getHeader().getExecuteTime();
                String timeTxEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
                System.out.println("事务结束时间: " + timeTxEnd);
                continue;
            }

            /// 需要: ROWDATA 类型的entry
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN
                    || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChange = null;
            try {
                rowChange = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
            }

            /// QUERY UPDATE
            EventType eventType = rowChange.getEventType();

            if (Objects.equals(eventType, EventType.QUERY)) {
                continue;
            }

            System.out.println(String.format("-------> binlog: [%s: %s], name: [%s, %s], eventType: %s <-------\n",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChange.getRowDatasList()) {
                System.out.println(eventType.toString());
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList(), null);
                } else if (eventType == EventType.INSERT) {
                    printColumn(null, rowData.getAfterColumnsList());
                } else if (eventType == EventType.UPDATE) {
                    printColumn(rowData.getBeforeColumnsList(), rowData.getAfterColumnsList());
                }
            }
        }
    }

    public static void printColumn(List<Column> beforeColumns, List<Column> afterColumns) {
        // insert操作
        if (beforeColumns == null || beforeColumns.size() == 0) {
            for (int i = 0; i < afterColumns.size(); i++) {
                Column afterColumn = afterColumns.get(i);
                System.out.println(afterColumn.getName() + "\t" + "" + "\t" +
                        afterColumn.getValue() + "\t" + "true");
            }
            return;
        }
        // delete操作
        if (afterColumns == null || afterColumns.size() == 0) {
            for (int i = 0; i < beforeColumns.size(); i++) {
                Column beforeColumn = beforeColumns.get(i);
                System.out.println(beforeColumn.getName() + "\t" + beforeColumn.getValue() + "\t" +
                        "" + "\t" + "true");
            }
            return;
        }
        // update操作
        if (beforeColumns.size() != 0 || afterColumns.size() != 0) {
            for (int i = 0; i < afterColumns.size(); i++) {
                Column beforeColumn = beforeColumns.get(i);
                Column afterColumn = afterColumns.get(i);

                System.out.println("列信息: " + afterColumn.getName() +
                        ":" + afterColumn.getMysqlType() +
                        ":" + afterColumn.getSqlType() + "\t修改前后的值: ");

                System.out.println("--------------------------");

                String value = afterColumn.getValue();
                String type = afterColumn.getMysqlType();
                if (
                        type.contains("decimal")
                                || type.contains("float")
                                || type.contains("double")
                                || type.contains("numeric")
                                || type.contains("real")
                ) {
                    if (StringUtils.isNotBlank(value)) {
                        System.out.println(afterColumn.getName() + ":" + type + ":" + value);
                        Double d = Double.valueOf(value);
                        value = d.toString().replace(".0", "");
                        System.out.println(afterColumn.getName() + ":" + type + ":" + value);
                    }
                }
                System.out.println("--------------------------");

                System.out.println(afterColumn.getName() + "\t" + beforeColumn.getValue() + "\t" +
                        afterColumn.getValue() + "\t" + afterColumn.getUpdated());
            }
        }
    }
}