package org.luvx.boot.tools.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.BaseAppTests;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

@Slf4j
class MainTest extends BaseAppTests {
    @Value("${org.luvx.description}")
    private String              desc;
    @Value("#{'${org.luvx.description}'.split(' ')}")
    private List<String>        list;
    @Value("#{${org.luvx.map}}")
    private Map<String, String> map;

    @Test
    void m1() {
        log.info("{} {} {}", desc, list, map);
    }
}
