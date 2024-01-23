package com.br93.testbackend.service;

import org.springframework.stereotype.Service;

import com.br93.testbackend.data.Product;
import com.br93.testbackend.exception.ProductInvalidException;
import com.br93.testbackend.repository.ProductRepository;
import com.br93.testbackend.util.validation.ProductValidation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductValidation productValidation;

    public Product createProduct(Product product) {

        if (!this.productValidation.isValidProduct(product)) {
            throw new ProductInvalidException("Product invalid");
        }

        return this.productRepository.save(product);
    }
    
}
