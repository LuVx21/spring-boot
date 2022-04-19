package org.luvx.fund.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataType {
    WORTH(1),
    GRAND(2),
    ;
    private final int value;
}
