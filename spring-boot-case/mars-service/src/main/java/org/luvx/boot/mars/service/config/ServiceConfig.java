package org.luvx.boot.mars.service.config;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ComponentScan({"org.luvx.boot.mars.service"})
@RetrofitScan("org.luvx.boot.mars.service.api")
public class ServiceConfig {
}
