package org.luvx.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ren, Xie
 */
@MapperScan(basePackages = {"org.luvx.mapper"})
@Configuration
public class MybatisConfig {
}