package org.luvx.boot.mars.runner.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({"org.luvx.boot.mars.runner"})
public class RunnerConfig {
}
