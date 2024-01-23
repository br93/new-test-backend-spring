package com.br93.testbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.Product;
import com.br93.testbackend.exception.ProductInvalidException;
import com.br93.testbackend.repository.ProductRepository;
import com.br93.testbackend.util.validation.ProductValidation;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductValidation productValidation;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private Producer producer;

    private Product mockProduct;
    private Category mockCategory;

    private final String title = "title";
    private final String description = "description";
    private final String ownerId = "ownerId";

    @BeforeEach
    void init() {

        mockCategory = new Category(null, title, description, ownerId);
        mockProduct = new Product(null, title, description, BigDecimal.ONE, mockCategory, ownerId);
    }

    @Test
    void createProductShouldReturnNewProduct() {
        when(productValidation.isValidProduct(any(Product.class))).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        Product newProduct = productService.createProduct(mockProduct);

        assertEquals(mockProduct.getTitle(), newProduct.getTitle());
    }

    @Test
    void createProductShouldThrowProductInvalidExceptionIfProductInvalid() {

        when(productValidation.isValidProduct(any(Product.class))).thenReturn(false);
        assertThrowsExactly(ProductInvalidException.class, () -> productService.createProduct(mockProduct));
    }
}
