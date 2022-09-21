package org.luvx.tools.dao.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Slf4j
@Configuration
@EnableJdbcRepositories(basePackages = {"org.luvx.tools.dao"})
public class DaoConfig {
}
