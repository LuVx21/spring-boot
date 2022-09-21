package org.luvx.tools.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ComponentScan({"org.luvx.tools.dao"})
@MapperScan({"org.luvx.tools.dao.mapper"})
public class DaoConfig {
}
