package org.luvx.boot.mars.rpc.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDubbo(scanBasePackages = {"org.luvx.boot.mars.rpc"})
public class RpcConfig {
}
