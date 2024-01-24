package com.br93.testbackend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.br93.testbackend.container.MongoDBTestContainer;
import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.Product;

@DataMongoTest
class ProductRepositoryTest extends MongoDBTestContainer {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category mockCategory;
    private String categoryId;

    private Product mockProduct;
    private Product mockProduct2;

    private final String ownerId = UUID.randomUUID().toString();

    @AfterEach
    void cleanup() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @BeforeEach
    void setup() {
    
        mockCategory = new Category(null, "title", "description", ownerId);
        mockProduct = new Product(null, "title1", "description1", BigDecimal.ONE, mockCategory, ownerId);
        mockProduct2 = new Product(null, "title2", "description2", BigDecimal.TEN, mockCategory, ownerId);

        categoryId = categoryRepository.save(mockCategory).getId();
        productRepository.save(mockProduct);
        productRepository.save(mockProduct2);
    }

    @Test
    void findAllByCategory_IdShouldReturnProductsBySaidCategory() {

        List<Product> products = productRepository.findAllByCategory_Id(categoryId);

        assertAll(
                () -> assertEquals(2, products.size()),
                () -> assertTrue(products.contains(mockProduct)),
                () -> assertTrue(products.contains(mockProduct2)),
                () -> assertEquals(categoryId, products.get(0).getCategory().getId()));
    }

}
