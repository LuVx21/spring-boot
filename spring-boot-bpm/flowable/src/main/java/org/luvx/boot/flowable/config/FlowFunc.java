package org.luvx.boot.flowable.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.luvx.coding.common.util.MoreTextUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("customFunc")
public class FlowFunc {
    public boolean inFunc(String id, String strIds) {
        return containsFunc(strIds, id);
    }

    public boolean containsFunc(String strIds, String id) {
        log.info("customFunc.containsFunc参数->strIds:{},id:{}", strIds, id);
        if (StringUtils.isEmpty(strIds) || StringUtils.isEmpty(id)) {
            return false;
        }

        boolean result = MoreTextUtils.parse2List(strIds)
                .contains(id);
        log.info("customFunc.containsFunc结果:{}", result);
        return result;
    }
}
