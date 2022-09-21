package org.luvx.tools.dao.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan({"org.luvx.tools.dao"})
public class DaoConfig {
}
