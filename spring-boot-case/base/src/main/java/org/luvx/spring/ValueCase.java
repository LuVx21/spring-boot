package org.luvx.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Component
public class ValueCase {
    @Value("${org.luvx.description}")
    private String desc;
    @Value("#{'${org.luvx.description}'.split(' ')}")
    private List<String> list;
    @Value("#{${value.map}}")
    private Map<String, String> map;

    @PostConstruct
    public void init() {
        log.info("{} {} {}", desc, list, map);
    }
}
