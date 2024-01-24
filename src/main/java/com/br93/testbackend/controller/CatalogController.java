package com.br93.testbackend.controller;

import java.io.InputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.minio.FileService;
import com.br93.testbackend.minio.MinioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final MinioService minioService;
    private final FileService fileService;
    
    @GetMapping
    public ResponseEntity<CatalogJSON> getCatalog() {
        InputStream object = minioService.getObject("catalog.json");
        CatalogJSON catalog = fileService.convertToCatalogJSON(object);
       
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }
}
