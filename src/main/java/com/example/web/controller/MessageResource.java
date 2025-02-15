package com.example.web.controller;

import com.example.web.request.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageResource {

    private final AmqpTemplate rabbitTemplate;

    @PostMapping("/send/{exchange}/{key}")
    public String sendMessage(@PathVariable String exchange,
                              @PathVariable String key,
                              @RequestBody UserMessage userMessage) {
        rabbitTemplate.convertAndSend(exchange, key, userMessage);
        return String.format("Message sent to exchange %s with key %s", exchange, key);
    }

    @PostMapping("/send/{exchange}")
    public String sendFanoutMessage(@PathVariable String exchange,
                                    @RequestBody UserMessage userMessage) {
        rabbitTemplate.convertAndSend(exchange, "", userMessage);
        return String.format("Message sent to fanout exchange %s", exchange);
    }

}