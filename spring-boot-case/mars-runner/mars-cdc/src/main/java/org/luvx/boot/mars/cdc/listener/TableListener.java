package org.luvx.boot.mars.cdc.listener;

import com.alibaba.fastjson2.JSONObject;
import io.debezium.data.Envelope.Operation;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mars.cdc.listener.event.DebeziumEventData;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class TableListener extends DebeziumBaseListener {
    @Override
    public void processValue(Operation op, JSONObject schema, DebeziumEventData.ChangeData cdcData) {
        JSONObject before = cdcData.getBefore();
        JSONObject after = cdcData.getAfter();
        switch (op) {
            case CREATE -> log.info("insert操作");
            case DELETE -> log.info("delete操作");
            case UPDATE -> log.info("update操作");
        }
        log.info("监听表({}.{})数据修改", cdcData.getSource().getString("db"), cdcData.getSource().getString("table"));
        log.info("修改前:{}", before);
        log.info("修改后:{}", after);
    }

    @Override
    protected Set<String> listenTables() {
        return Set.of("boot.user");
    }

    @Override
    public Set<Operation> listenEventType() {
        return Set.of(Operation.CREATE, Operation.DELETE, Operation.UPDATE);
    }
}
