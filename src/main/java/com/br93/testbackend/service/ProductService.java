package com.br93.testbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br93.testbackend.data.Product;
import com.br93.testbackend.exception.ProductInvalidException;
import com.br93.testbackend.exception.ProductNotFoundException;
import com.br93.testbackend.rabbitmq.Message;
import com.br93.testbackend.rabbitmq.Producer;
import com.br93.testbackend.repository.ProductRepository;
import com.br93.testbackend.util.validation.ProductValidation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductValidation productValidation;
    private final Producer producer;

    public Product createProduct(Product product) {

        if (!this.productValidation.isValidProduct(product)) {
            throw new ProductInvalidException("Product invalid");
        }

        this.producer.send(Message.builder().owner(product.getOwnerId()).build());

        return this.productRepository.save(product);
    }

    public Optional<Product> findProductById(String id) {
        return this.productRepository.findById(id);
    }

    public Product updateProduct(String id, Product newProduct) {
        var product = this.findProductById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (!newProduct.getTitle().isEmpty())
            product.setTitle(newProduct.getTitle());
        if (!newProduct.getDescription().isEmpty())
            product.setDescription(newProduct.getDescription());
        if (newProduct.getPrice() != null)
            product.setPrice(newProduct.getPrice());
        if (newProduct.getCategory() != null)
            product.setCategory(newProduct.getCategory());

        this.producer.send(Message.builder().owner(product.getOwnerId()).build());

        return this.createProduct(product);
    }

    public void deleteProduct(String id) {
        Product product = this.findProductById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        this.producer.send(Message.builder().owner(product.getOwnerId()).build());
        this.productRepository.deleteById(id);
    }

    public List<Product> findAllProductsByCategoryId(String categoryId) {
        return this.productRepository.findAllByCategory_Id(categoryId);
    }

}
