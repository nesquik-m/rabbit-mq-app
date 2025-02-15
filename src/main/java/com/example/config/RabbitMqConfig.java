package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    Queue firstQueue() {
        return new Queue("first.queue", false);
    }

    @Bean
    Queue secondQueue() {
        return new Queue("second.queue", false);
    }

    @Bean
    Queue thirdQueue() {
        return new Queue("third.queue", false);
    }

    // DIRECT

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("our.direct.exchange");
    }

    @Bean
    Binding firstQueueToOurDirectExchangeBinding() {
        return BindingBuilder
                .bind(firstQueue())
                .to(directExchange())
                .with("first.queue");
    }

    @Bean
    Binding secondQueueToOurDirectExchangeBinding() {
        return BindingBuilder
                .bind(secondQueue())
                .to(directExchange())
                .with("second.queue");
    }

    @Bean
    Binding thirdQueueToOurDirectExchangeBinding() {
        return BindingBuilder
                .bind(thirdQueue())
                .to(directExchange())
                .with("third.queue");
    }

}