package org.luvx.boot.web;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.web.exchange.UserClient;
import org.luvx.coding.common.util.JsonUtils;

@Slf4j
public class UserClientTest extends RestAppTests {
    @Resource
    private UserClient service;

    @Test
    void m1() {
        String haha = service.select(1);
        log.info(JsonUtils.toPrettyJson(haha));
    }
}
