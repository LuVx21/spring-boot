package com.luvx.conf.xxl.controller;

import com.google.common.collect.ImmutableList;
import com.xxl.conf.core.XxlConfClient;
import com.xxl.conf.core.annotation.XxlConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class IndexController {

    static {
        XxlConfClient.addListener("default.key01", (key, value) -> log.info("配置变更事件通知：{}={}", key, value));
    }

    @XxlConf("default.key02")
    public String paramByAnno;

    @RequestMapping("")
    public List<Object> index() {
        return ImmutableList.of(
                "1、API方式: default.key01=" + XxlConfClient.get("default.key01", null),
                "2、@XxlConf 注解方式: default.key02=" + paramByAnno
        );
    }
}
