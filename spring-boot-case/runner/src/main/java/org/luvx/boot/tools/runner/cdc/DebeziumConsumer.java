package org.luvx.boot.tools.runner.cdc;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.debezium.data.Envelope;
import io.debezium.data.Envelope.FieldName;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.DebeziumEngine.ChangeConsumer;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.runner.cdc.listener.event.DebeziumEvent;
import org.luvx.boot.tools.runner.cdc.listener.event.DebeziumEventData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;
import java.util.List;

@Slf4j
@Configuration
public class DebeziumConsumer implements ChangeConsumer<ChangeEvent<String, String>> {
    @Resource
    private ApplicationEventPublisher publisher;

    @Override
    public void handleBatch(List<ChangeEvent<String, String>> records, DebeziumEngine.RecordCommitter<ChangeEvent<String, String>> committer) throws InterruptedException {
        for (ChangeEvent<String, String> r : records) {
            // 循环体内抛出异常会导致后面的日志监听失败
            JSONObject key = JSON.parseObject(r.key());
            if (r.value() == null) {
                committer.markProcessed(r);
                continue;
            }
            JSONObject value = JSON.parseObject(r.value());
            JSONObject schema = value.getJSONObject("schema");
            JSONObject payload = value.getJSONObject("payload");

            JSONObject before = payload.getJSONObject(FieldName.BEFORE);
            JSONObject after = payload.getJSONObject(FieldName.AFTER);
            JSONObject source = payload.getJSONObject(FieldName.SOURCE);
            Envelope.Operation op = Envelope.Operation.forCode(payload.getString(FieldName.OPERATION));
            if (op == null) {
                committer.markProcessed(r);
                continue;
            }
            Long tsMs = payload.getLong(FieldName.TIMESTAMP);
            JSONObject transaction = payload.getJSONObject(FieldName.TRANSACTION);

            DebeziumEventData.ChangeData cdcData = new DebeziumEventData.ChangeData(before, after, source, op, tsMs, transaction);

            DebeziumEventData changeEventData = new DebeziumEventData(key, schema, cdcData);
            DebeziumEvent debeziumEvent = new DebeziumEvent(op, changeEventData);
            publisher.publishEvent(debeziumEvent);

            committer.markProcessed(r);
        }
        committer.markBatchFinished();
    }
}
