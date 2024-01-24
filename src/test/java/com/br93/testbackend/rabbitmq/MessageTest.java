package com.br93.testbackend.rabbitmq;

import static org.awaitility.Awaitility.await;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import com.br93.testbackend.container.RabbitMQTestContainer;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class MessageTest extends RabbitMQTestContainer {

    @Autowired
    private Producer producer;

    private Message message;

    @BeforeEach
    void setup() {
        message = new Message("TEST");
    }

    @Test
    void shouldProduceMessageAndConsumeMessageSavingLogs(CapturedOutput output) {

        producer.send(message);
        await().atMost(5, TimeUnit.SECONDS).until(() -> output.toString().contains("TEST"));
    }

}
