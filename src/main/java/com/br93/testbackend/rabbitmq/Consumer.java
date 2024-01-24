package com.br93.testbackend.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = "catalog-emit")
    public void receive(@Payload Message message) {

        logger.info("Message received: {}", message);
    }
}
