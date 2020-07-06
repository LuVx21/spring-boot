package org.luvx.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ren, Xie
 */
// @ImportResource({"classpath:config/mybatis/mybatis-config.xml"})
@MapperScan(basePackages = {"org.luvx.mapper"})
@Configuration
public class MybatisConfig {
}