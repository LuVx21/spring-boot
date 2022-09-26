package org.luvx.tools.hybrid.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitScan;

import lombok.extern.slf4j.Slf4j;

/**
 * https://juejin.cn/post/6854573211426750472
 */
@Slf4j
@Configuration
@ComponentScan({"org.luvx.tools.hybrid"})
@RetrofitScan("org.luvx.tools.hybrid.retrofit")
public class HybridConfig {
}
