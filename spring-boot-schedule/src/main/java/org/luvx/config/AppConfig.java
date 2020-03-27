package org.luvx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName: org.luvx.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 15:03
 */
@Configuration
@EnableScheduling
@EnableAsync
public class AppConfig {
}
