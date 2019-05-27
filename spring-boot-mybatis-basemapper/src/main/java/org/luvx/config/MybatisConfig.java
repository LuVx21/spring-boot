package org.luvx.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "org.luvx.mapper")
@Configuration
public class MybatisConfig {
}