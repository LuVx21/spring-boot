package org.luvx.boot.tools.web.base.spring.listener.event;

import org.luvx.boot.common.listener.base.BaseEventData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyEvent1Data extends BaseEventData {
    private long id;
}
