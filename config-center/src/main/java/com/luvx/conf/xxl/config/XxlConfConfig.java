package com.luvx.conf.xxl.config;

import com.xxl.conf.core.spring.XxlConfFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class XxlConfConfig {
    @Value("${xxl.conf.admin.address}")
    private String adminAddress;

    @Value("${xxl.conf.env}")
    private String env;

    @Value("${xxl.conf.access.token}")
    private String accessToken;

    @Value("${xxl.conf.mirrorfile}")
    private String mirrorfile;

    @Bean
    public XxlConfFactory xxlConfFactory() {
        XxlConfFactory xxlConf = new XxlConfFactory();
        xxlConf.setAdminAddress(adminAddress);
        xxlConf.setEnv(env);
        xxlConf.setAccessToken(accessToken);
        xxlConf.setMirrorfile(mirrorfile);
        log.info(">>>>>>>>>>> xxl-conf config init.");
        return xxlConf;
    }
}