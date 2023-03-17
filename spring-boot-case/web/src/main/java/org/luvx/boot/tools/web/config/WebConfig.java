package org.luvx.boot.tools.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// @EnableSwagger2WebMvc
@ComponentScan({"org.luvx.boot.tools.web"})
public class WebConfig {
}
