package com.br93.testbackend.minio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.exception.ConversionException;
import com.br93.testbackend.util.Parser;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
class FileServiceTest {

    @Autowired
    private FileService fileService;

    private Object mockObject;
    private final String mockedMessage = "Mocked exception";

    @BeforeEach
    void setup() {
        mockObject = mock(Object.class);
    }

    @Test
    void shouldConvertToInputStream() throws Exception {
        
        String jsonString = "{\"key\":\"value\"}";

        try (MockedStatic<Parser> parserMock = mockStatic(Parser.class)) {
            parserMock.when(() -> Parser.parsePretty(mockObject)).thenReturn(jsonString);

            InputStream result = fileService.convertToInputStream(mockObject);
            assertNotNull(result);
        }
    }

    @Test
    void shouldThrowConversionExceptionIfJsonProcessingException() throws Exception {
      
        try (MockedStatic<Parser> parserMock = mockStatic(Parser.class)) {
            parserMock.when(() -> Parser.parsePretty(mockObject))
                    .thenThrow(new JsonProcessingException(mockedMessage) {
                    });

            assertThrowsExactly(ConversionException.class, () -> fileService.convertToInputStream(mockObject));
        }
    }

    @Test
    void shouldConvertToCatalogJSON() {
        
        String json = "{\"key\":\"value\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());

        try (MockedStatic<Parser> parserMock = mockStatic(Parser.class)) {
            parserMock.when(() -> Parser.toCatalog(json)).thenReturn(new CatalogJSON());

            CatalogJSON result = fileService.convertToCatalogJSON(inputStream);
            assertNotNull(result);
        } 
    }

    @Test
    void shouldThrowConversionExceptionIfIOException() {
                
        ByteArrayInputStream inputStream = mock(ByteArrayInputStream.class);
        when(inputStream.readAllBytes()).thenThrow(new IOException(mockedMessage));
        
        assertThrowsExactly(ConversionException.class, () -> fileService.convertToCatalogJSON(inputStream));
    }
}