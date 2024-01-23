package com.br93.testbackend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br93.testbackend.data.Product;
import com.br93.testbackend.exception.ProductInvalidException;
import com.br93.testbackend.exception.ProductNotFoundException;
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

    public Optional<Product> findProductById(String id) {
        return this.productRepository.findById(id);
    }

    public Product updateProduct(String id, Product newProduct) {
        var product = this.findProductById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (!newProduct.getTitle().isEmpty()) product.setTitle(newProduct.getTitle());
        if (!newProduct.getDescription().isEmpty()) product.setDescription(newProduct.getDescription());
        if (newProduct.getPrice() != null) product.setPrice(newProduct.getPrice());
        if (newProduct.getCategory() != null) product.setCategory(newProduct.getCategory());

        return this.createProduct(product);
    }

    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }
    
}
