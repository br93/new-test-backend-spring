package com.br93.testbackend.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.exception.BucketNotFoundException;
import com.br93.testbackend.exception.ConversionException;
import com.br93.testbackend.exception.ObjectException;
import com.br93.testbackend.minio.FileService;
import com.br93.testbackend.minio.MinioService;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private MinioService minioService;

        @MockBean
        private FileService fileService;

        private byte[] mockData;
        private InputStream mockInputStream;

        @BeforeEach
        void setup() {
                mockData = "Mocked data content".getBytes();
                mockInputStream = new ByteArrayInputStream(mockData);
        }

        @Test
        void shouldGetCatalogStatus200_Success() throws Exception {

                when(minioService.getObject("catalog.json")).thenReturn(mockInputStream);
                when(fileService.convertToCatalogJSON(any(InputStream.class)))
                                .thenReturn(new CatalogJSON("mocked-owner", null));

                mockMvc.perform(get("/api/v1/catalog"))
                                .andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON),
                                                jsonPath("$.owner").value("mocked-owner"));
        }

        @Test
        void shouldGetCatalogStatus500_BucketException() throws Exception {
                when(minioService.getObject("catalog.json")).thenThrow(new BucketNotFoundException("Mocked exception"));

                MvcResult result = mockMvc.perform(get("/api/v1/catalog"))
                                .andExpect(status().isInternalServerError()).andReturn();

                String content = result.getResponse().getContentAsString();
                assertTrue(content.contains("Mocked exception"));
        }

        @Test
        void should_GetCatalogStatus500_ObjectException() throws Exception {
                when(minioService.getObject("catalog.json")).thenThrow(new ObjectException("Mocked exception"));

                MvcResult result = mockMvc.perform(get("/api/v1/catalog"))
                                .andExpect(status().isInternalServerError()).andReturn();

                String content = result.getResponse().getContentAsString();
                assertTrue(content.contains("Mocked exception"));
        }

        @Test
        void should_GetCatalogStatus500_ConversionException() throws Exception {

                when(minioService.getObject("catalog.json")).thenReturn(mockInputStream);
                when(fileService.convertToCatalogJSON(any(InputStream.class)))
                                .thenThrow(new ConversionException("Mocked exception"));

                MvcResult result = mockMvc.perform(get("/api/v1/catalog"))
                                .andExpect(status().isInternalServerError()).andReturn();

                String content = result.getResponse().getContentAsString();
                assertTrue(content.contains("Mocked exception"));
        }
}
