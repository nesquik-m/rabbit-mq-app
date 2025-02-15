package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
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

    // DIRECT - индивидуальная доставка

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

    // TOPIC - индивидуальная доставка

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("our.topic.exchange");
    }

    @Bean
    Binding firstQueueToOurTopicExchangeBinding() {
        return BindingBuilder
                .bind(firstQueue())
                .to(topicExchange())
                .with("first.*.queue");
        /* .with поддерживает использование шаблонных символов:
            1) # - routingKey содержит любое количество сегментов, и заканчивается на [.queue]
                .with("#.queue");
                    aaa.queue
                    aaa.bbb.queue
                    aaa.bbb.ccc.queue
                .with("third.#.queue");
                    third.aaa.queue
                    third.aaa.bbb.queue
            2) * - routingKey содержит 1 сегмент, и заканчивается на [.queue]
                .with("*.queue");
                    aaa.queue
                .with("third.*.queue");
                    third.aaa.queue
         */
    }

    // FANOUT - широковещательный формат

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("our.fanout.exchange");
    }

    @Bean
    Binding firstQueueToOurFanoutExchangeBinding() {
        return BindingBuilder
                .bind(firstQueue())
                .to(fanoutExchange());
    }

    @Bean
    Binding secondQueueToOurFanoutExchangeBinding() {
        return BindingBuilder
                .bind(secondQueue())
                .to(fanoutExchange());
    }

    @Bean
    Binding thirdQueueToOurFanoutExchangeBinding() {
        return BindingBuilder
                .bind(thirdQueue())
                .to(fanoutExchange());
    }

}