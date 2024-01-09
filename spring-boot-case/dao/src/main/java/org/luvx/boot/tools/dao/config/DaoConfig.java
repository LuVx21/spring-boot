package org.luvx.boot.tools.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ComponentScan({"org.luvx.boot.tools.dao"})
@MapperScan({"org.luvx.boot.tools.dao.mapper"})
public class DaoConfig {
}
