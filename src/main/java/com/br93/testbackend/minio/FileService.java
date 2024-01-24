package com.br93.testbackend.minio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.exception.ConversionException;
import com.br93.testbackend.util.Parser;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public InputStream convertToInputStream(Object object) {

        String json;

        try {
            json = Parser.parsePretty(object);
            return new ByteArrayInputStream(json.getBytes());

        } catch (JsonProcessingException e) {
            String message = "Convertion failed: " + e.getMessage();
            logger.warn(message);
            throw new ConversionException(message);
        }

    }

    public CatalogJSON convertToCatalogJSON(InputStream inputStream) {
        byte[] bytes;

        try {
            bytes = inputStream.readAllBytes();
            return Parser.toCatalog(new String(bytes));

        } catch (IOException e) {
            String message = "Convertion failed: " + e.getMessage();
            logger.warn(message);
            throw new ConversionException(message);
        }

    }

}