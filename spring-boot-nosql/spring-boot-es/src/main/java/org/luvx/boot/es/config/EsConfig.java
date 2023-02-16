package org.luvx.boot.es.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.springframework.data.elasticsearch.core.query.Query;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.util.ReflectionUtils;

@Slf4j
@Configuration
public class EsConfig {
    // @Bean(destroyMethod = "close")
}
