package org.luvx.boot.web.config;

import org.luvx.boot.web.exchange.UserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebConfig {
    @Bean
    WebClient webClient() {
        return WebClient.builder()
//                .baseUrl("https://gitee.com/api/v5")
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Bean
    UserClient toDoService() {
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient()))
                .build()
                .createClient(UserClient.class);
    }
}
