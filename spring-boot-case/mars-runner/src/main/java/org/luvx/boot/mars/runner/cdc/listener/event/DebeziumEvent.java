package org.luvx.boot.mars.runner.cdc.listener.event;

import io.debezium.data.Envelope;
import org.luvx.boot.common.listener.base.BaseEvent;

public class DebeziumEvent extends BaseEvent<Envelope.Operation, DebeziumEventData> {
    public DebeziumEvent(Envelope.Operation type, DebeziumEventData data) {
        super(type, data);
    }
}
