package org.luvx.boot.mars.rpc.common.count;

import lombok.AllArgsConstructor;
import org.luvx.coding.common.enums.primitives.EnumHasIntCode;

@AllArgsConstructor
public enum CountEvent implements EnumHasIntCode {
    SET_COUNT(1),
    INC_COUNT(2),
    DEC_COUNT(3),
    ;

    private final int code;

    @Override
    public int intCode() {
        return code;
    }
}