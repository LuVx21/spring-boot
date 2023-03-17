package org.luvx.boot.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"org.luvx.boot", "org.luvx.boot.tools.*"})
public class TestConfiguration {
}
