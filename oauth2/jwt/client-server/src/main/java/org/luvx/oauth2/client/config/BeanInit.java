package org.luvx.oauth2.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeanInit {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
