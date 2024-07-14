package org.luvx.boot.mars.grpc.sdk.count;

import lombok.AllArgsConstructor;
import org.luvx.coding.common.enums.EnumHasCode;

@AllArgsConstructor
public enum CountEvent implements EnumHasCode<Integer> {
    SET_COUNT(1),
    INC_COUNT(2),
    DEC_COUNT(3),
    ;

    private final int code;

    @Override
    public Integer getCode() {
        return code;
    }
}