package org.luvx.boot.mars.service.count;

import org.luvx.boot.common.listener.base.BaseEvent;
import org.luvx.boot.mars.rpc.common.count.CountOperateType;
import org.luvx.boot.mars.rpc.common.count.CountType;

public class CountEvent extends BaseEvent<CountOperateType, CountEvent.CountEventData> {
    public CountEvent(CountOperateType type, CountEventData data) {
        super(type, data);
    }

    public record CountEventData(
            long countId, CountType type, int delta
    ) {
    }
}
