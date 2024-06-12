package org.luvx.boot.reactive.web.config;

import org.luvx.boot.reactive.web.exchange.UserClient;
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
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Bean
    UserClient toDoService() {
        WebClientAdapter adapter = WebClientAdapter.create(webClient());
        return HttpServiceProxyFactory.builderFor(adapter)
                .build()
                .createClient(UserClient.class);
    }
}
