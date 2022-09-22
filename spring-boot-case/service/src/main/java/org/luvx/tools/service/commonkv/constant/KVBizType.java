package org.luvx.tools.service.commonkv.constant;

import org.luvx.common.enums.EnumHasCode;

public interface KVBizType extends EnumHasCode<Integer> {
    /**
     * value 存储的数据类型
     */
    Class<?> getValueClass();

    /**
     * 业务场景
     */
    default Integer getBizType() {
        return getCode();
    }
}
