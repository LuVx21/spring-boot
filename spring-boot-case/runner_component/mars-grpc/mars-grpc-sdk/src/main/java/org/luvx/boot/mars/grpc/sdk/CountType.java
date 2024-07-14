package org.luvx.boot.mars.grpc.sdk;

import org.luvx.coding.common.enums.EnumHasCode;

public interface CountType extends EnumHasCode<Integer> {
    int getType();

    @Override
    default Integer getCode() {
        return getType();
    }
}
