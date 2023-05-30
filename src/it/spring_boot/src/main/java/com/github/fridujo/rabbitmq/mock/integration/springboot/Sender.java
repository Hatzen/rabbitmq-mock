package com.github.fridujo.rabbitmq.mock.integration.springboot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(int i) {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend("", AmqpApplication.QUEUE_NAME, "Hello " + i + " from RabbitMQ!");
    }
}
