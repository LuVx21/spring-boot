package org.luvx.boot.mars.cdc.listener.event;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import io.debezium.data.Envelope;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DebeziumEventData // extends BaseEventData
{
    /**
     * 以下为io.debezium.engine.ChangeEvent的key部分
     */
    private JSONObject key;

    /**
     * 以下为io.debezium.engine.ChangeEvent的value部分
     */
    private JSONObject scheme;
    private ChangeData cdcData;

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeData {
        /**
         * 更改前数据
         */
        private JSONObject         before;
        /**
         * 更改后数据
         */
        private JSONObject         after;
        private JSONObject         source;
        private Envelope.Operation op;
        @JSONField(name = "ts_ms")
        private Long               tsMs;
        private JSONObject         transaction;
    }
}
