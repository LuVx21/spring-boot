package org.luvx.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = {"org.luvx.mapper","org.luvx.anno.mapper"})
@Configuration
public class MybatisConfig {
}