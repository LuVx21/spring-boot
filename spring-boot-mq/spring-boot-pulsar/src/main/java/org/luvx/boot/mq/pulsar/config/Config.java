package org.luvx.boot.mq.pulsar.config;

import org.apache.pulsar.client.api.DeadLetterPolicy;
import org.apache.pulsar.client.api.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.DefaultSchemaResolver;
import org.springframework.pulsar.core.PulsarTopic;
import org.springframework.pulsar.core.SchemaResolver;

@Configuration
public class Config {

    @Bean
    DeadLetterPolicy deadLetterPolicy() {
        return DeadLetterPolicy.builder()
                .maxRedeliverCount(10)
                .deadLetterTopic(ConstValue.USER_DEAD_LETTER_TOPIC)
                .build();
    }

    // @Bean
    // PulsarTopic partitionedTopic() {
    //     return PulsarTopic.builder(TOPIC).numberOfPartitions(3).build();
    // }

    /**
     * 配置在了文件中
     */
    // @Bean
    public SchemaResolver.SchemaResolverCustomizer<DefaultSchemaResolver> schemaResolverCustomizer() {
        return schemaResolver -> schemaResolver.addCustomSchemaMapping(User.class, Schema.JSON(User.class));
    }
}
