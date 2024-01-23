package com.br93.testbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br93.testbackend.data.Product;
import com.br93.testbackend.data.dto.ProductDTO;
import com.br93.testbackend.service.ProductService;
import com.br93.testbackend.util.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO request) {

        Product product = this.productMapper.toEntity(request);
        Product created = this.productService.createProduct(product);
        ProductDTO response = this.productMapper.toDTO(created);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
