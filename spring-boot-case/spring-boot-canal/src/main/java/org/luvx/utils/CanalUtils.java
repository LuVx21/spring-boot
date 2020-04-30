package org.luvx.utils;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: org.luvx
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/5 10:48
 */
@Slf4j
public class CanalUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    public static CanalConnector getConnect(String ip, int port, String destination) {
        if (ip == null) {
            ip = AddressUtils.getHostIp();
        }
        if (port == 0) {
            port = 11111;
        }

        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(ip, port),
                destination,
                "",
                "");
        return connector;
    }

    public static void getBinlog(String ip, int port, String destination) {
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(ip, port),
                destination,
                "",
                "");

        int batchSize = 4 * 1024;
        int emptyCount = 0;

        try {
            connector.connect();
            connector.subscribe("");
            connector.rollback();

            int totalEmptyCount = 30;
            while (emptyCount < totalEmptyCount) {
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();

                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println(destination + "实例->" + "empty count : " + emptyCount);
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    emptyCount = 0;
                    CanalUtils.analysisBinlog(message.getEntries());
                }

                connector.ack(batchId);
            }

            System.out.println(destination + "实例->" + "empty too many times, exit");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
    }

    public static void analysisBinlog(List<Entry> entrys) throws Exception {
        for (Entry entry : entrys) {

            Header header = entry.getHeader();

            EntryType type = entry.getEntryType();
            long timestamp = header.getExecuteTime();
            String time = formatter.format(LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8)));
            String txId = TransactionEnd.parseFrom(entry.getStoreValue()).getTransactionId();

            log.info("事件类型:{}, 执行时间:{}, 事务id:{}, position:{}", type, time, txId, header.getLogfileOffset());

            if (type.equals(EntryType.TRANSACTIONBEGIN)
                    || type.equals(EntryType.TRANSACTIONEND)
            ) {
                continue;
            }

            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN) {
                long time1 = header.getExecuteTime();
                String timeTxStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time1);
                System.out.println("事务开始时间: " + timeTxStart);
                continue;
            } else if (entry.getEntryType() == EntryType.TRANSACTIONEND) {
                long time1 = header.getExecuteTime();
                String timeTxEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time1);
                System.out.println("事务结束时间: " + timeTxEnd);
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

            System.out.println(String.format("-------> binlog: [%s: %s], name: [%s, %s], eventType: %s <-------",
                    header.getLogfileName(), header.getLogfileOffset(),
                    header.getSchemaName(), header.getTableName(),
                    eventType));

            for (RowData rowData : rowChange.getRowDatasList()) {
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
                System.out.println("插入后的值: " + afterColumn.getName() + "\t" + "" + "\t" +
                        afterColumn.getValue() + "\t" + "true");
            }
            return;
        }
        // delete操作
        if (afterColumns == null || afterColumns.size() == 0) {
            for (int i = 0; i < beforeColumns.size(); i++) {
                Column beforeColumn = beforeColumns.get(i);
                System.out.println("删除前的值: " + beforeColumn.getName() + "\t" + beforeColumn.getValue() + "\t" +
                        "" + "\t" + "true");
            }
            return;
        }
        // update操作
        if (beforeColumns.size() != 0 || afterColumns.size() != 0) {
            for (int i = 0; i < afterColumns.size(); i++) {
                Column beforeColumn = beforeColumns.get(i);
                Column afterColumn = afterColumns.get(i);

                System.out.printf("%-40s", "列信息: "
                        + afterColumn.getName()
                        + ":"
                        + afterColumn.getMysqlType()
                );

                System.out.println("修改前后的值: "
                        + afterColumn.getUpdated() + "\t"
                        + beforeColumn.getValue() + "\t"
                        + afterColumn.getValue());
            }
        }
    }
}
