package org.luvx.spring.other;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.luvx.BaseAppTests;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

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
