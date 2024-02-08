package com.beaconfire.emailservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    Queue emailQueue() {
        return new Queue("emailQueue", true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("emailExchange");
    }

    @Bean
    Binding binding(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with("emailRoutingKey");
    }
}