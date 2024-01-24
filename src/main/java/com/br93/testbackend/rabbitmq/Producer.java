package com.br93.testbackend.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.br93.testbackend.config.RabbitMQConfig.RabbitMQProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {

    private final AmqpTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    public void send(Message message) {
        this.rabbitTemplate.convertAndSend(properties.getExchange(), properties.getRoutingKey(), message);
    }
    
}
