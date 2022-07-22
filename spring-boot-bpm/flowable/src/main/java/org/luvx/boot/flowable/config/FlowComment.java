package org.luvx.boot.flowable.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.luvx.common.enums.EnumHasCode;

@AllArgsConstructor
public enum FlowComment implements EnumHasCode<String> {
    NORMAL("1", "正常意见"),
    REBACK("2", "退回意见"),
    REJECT("3", "驳回意见"),
    DELEGATE("4", "委派意见"),
    ASSIGN("5", "转办意见"),
    STOP("6", "终止流程"),
    ;

    private final String code;
    @Getter
    private final String remark;

    @Override
    public String getCode() {
        return code;
    }
}
