package org.luvx.tools.service.commonkv;

import org.junit.jupiter.api.Test;
import org.luvx.common.more.MorePrints;
import org.luvx.tools.BaseAppTests;
import org.luvx.tools.dao.entity.CommonKeyValue;
import org.luvx.tools.dao.entity.User;
import org.luvx.tools.service.commonkv.constant.CommonKVBizType;
import org.springframework.beans.factory.annotation.Autowired;

class CommonKeyValueServiceTest extends BaseAppTests {
    @Autowired
    private CommonKeyValueService service;

    @Test
    void m1() {
        service.setValue(CommonKVBizType.INT, "a", 11);
        service.setOrIncrValue(CommonKVBizType.INT, "a", 11);
        CommonKeyValue a = service.get(CommonKVBizType.INT, "a");
        System.out.println(a);
    }

    @Test
    void m2() {
        User user = new User();
        user.setUserName("foo");
        user.setPassword("bar");
        service.setValue(CommonKVBizType.MAP, "a", user);

        CommonKeyValue a = service.get(CommonKVBizType.MAP, "a");
        Object a1 = service.getData(CommonKVBizType.MAP, "a");
        MorePrints.println(a, a1);
    }
}