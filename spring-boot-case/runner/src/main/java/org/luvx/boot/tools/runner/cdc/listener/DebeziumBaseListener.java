package org.luvx.boot.tools.runner.cdc.listener;

import com.alibaba.fastjson2.JSONObject;
import io.debezium.data.Envelope.Operation;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.common.listener.base.BaseEventListener;
import org.luvx.boot.tools.runner.cdc.listener.event.DebeziumEvent;
import org.luvx.boot.tools.runner.cdc.listener.event.DebeziumEventData;

import java.util.Set;

@Slf4j
public abstract class DebeziumBaseListener extends BaseEventListener<DebeziumEvent, Operation, DebeziumEventData> {
    private final Set<Operation> listenEventTypes = Set.of(Operation.values());

    @Override
    protected void onEvent(Operation op, DebeziumEventData data) {
        if (op == null || !listenEventType().contains(op)) {
            return;
        }

        DebeziumEventData.ChangeData cdcData = data.getCdcData();
        JSONObject source = cdcData.getSource();
        String table = STR."\{source.getString("db")}.\{source.getString("table")}";
        Set<String> listenTables = listenTables();
        if (!listenTables.isEmpty() && !listenTables.contains(table)) {
            return;
        }

        processKey(data.getKey());
        processValue(op, data.getScheme(), cdcData);
    }

    @Override
    protected Set<Operation> listenEventType() {
        return listenEventTypes;
    }

    /**
     * 空则监听已经配置的所有
     */
    protected Set<String> listenTables() {
        return Set.of();
    }

    protected void processKey(JSONObject key) {
    }

    protected abstract void processValue(Operation op, JSONObject schema, DebeziumEventData.ChangeData cdcData);
}
