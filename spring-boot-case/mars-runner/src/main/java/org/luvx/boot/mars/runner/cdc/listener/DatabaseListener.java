package org.luvx.boot.mars.runner.cdc.listener;

import com.alibaba.fastjson2.JSONObject;
import io.debezium.data.Envelope.Operation;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mars.runner.cdc.listener.event.DebeziumEventData;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class DatabaseListener extends DebeziumBaseListener {
    @Override
    public void processValue(Operation op, JSONObject schema, DebeziumEventData.ChangeData cdcData) {
        JSONObject before = cdcData.getBefore();
        JSONObject after = cdcData.getAfter();
        log.info("监听到库({})数据修改", cdcData.getSource().getString("db"));
        log.info("修改前:{}", before);
        log.info("修改后:{}", after);
    }

    @Override
    public Set<Operation> listenEventType() {
        return Set.of(Operation.UPDATE);
    }
}
