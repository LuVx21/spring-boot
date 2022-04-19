package org.luvx.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
    @Scope("singleton")
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public SmallBean smallBean() {
        return new SmallBean();
    }
}
