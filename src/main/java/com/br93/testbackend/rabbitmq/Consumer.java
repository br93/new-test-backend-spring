package com.br93.testbackend.rabbitmq;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.minio.FileService;
import com.br93.testbackend.minio.MinioService;
import com.br93.testbackend.service.CatalogService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Consumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CatalogService catalogService;
    private final FileService fileService;
    private final MinioService minioService;

    @RabbitListener(queues = "catalog-emit")
    public void receive(@Payload Message message) {
        logger.info("Message received: {}", message);

        CatalogJSON catalog = this.catalogService.createCatalog(message.getOwner());
        InputStream inputStream = this.fileService.convertToInputStream(catalog);
        this.minioService.putObject(inputStream, "catalog.json", "application/json");
    }
}
