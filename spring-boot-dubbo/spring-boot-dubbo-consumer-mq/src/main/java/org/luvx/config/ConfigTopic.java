package org.luvx.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topicexchange配置,1个exchagne绑定2个队列
 */
@Configuration
public class ConfigTopic {
    private static final String queue_rabbit = "rabbit";
    private static final String queue_fish = "fish";
    private static final String exchange_topic = "LuVx-TopicExchange";

    @Bean
    public Queue queueRabbit() {
        return new Queue(queue_rabbit);
    }

    @Bean
    public Queue queueFish() {
        return new Queue(queue_fish);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange_topic);
    }

    @Bean
    public Binding bindTopicExchangWithRabbit(TopicExchange topicExchange, Queue queueRabbit) {
        return BindingBuilder.bind(queueRabbit).to(topicExchange).with("rabbit.*");
    }

    @Bean
    public Binding bindTopicExchangWithFish(TopicExchange topicExchange, Queue queueFish) {
        return BindingBuilder.bind(queueFish).to(topicExchange).with("*.fish");
    }
}
