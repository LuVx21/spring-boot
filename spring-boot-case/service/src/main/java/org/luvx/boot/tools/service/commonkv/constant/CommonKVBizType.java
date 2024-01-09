package org.luvx.boot.tools.service.commonkv.constant;

import java.util.List;
import java.util.Map;

import org.luvx.boot.tools.dao.entity.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommonKVBizType implements KVBizType {
    UNKNOWN(0, Object.class),
    INT(1, Integer.class),
    STRING(2, String.class),
    MAP(3, Map.class),
    BEAN(4, User.class),
    LIST(5, List.class),
    ARRAY(6, String[].class),
    ;

    private final int      bizType;
    private final Class<?> valueClass;

    @Override
    public Integer getCode() {
        return bizType;
    }

    @Override
    public Class<?> getValueClass() {
        return valueClass;
    }
}
