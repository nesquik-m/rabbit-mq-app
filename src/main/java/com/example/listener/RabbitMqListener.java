package com.example.listener;

import com.example.web.request.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "first.queue")
//@RabbitListener(queues = {"first.queue", "second.queue"})
//@RabbitListener(queues = {"first.queue", "second.queue", "third.queue"})
@Slf4j
public class RabbitMqListener {

    @RabbitHandler
    public void onMessage(UserMessage userMessage) {
        log.info("New message from first.queue {}", userMessage);
    }

}