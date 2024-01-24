package com.br93.testbackend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.br93.testbackend.container.MongoDBTestContainer;
import com.br93.testbackend.data.Category;

@DataMongoTest
class CategoryRepositoryTest extends MongoDBTestContainer {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category mockCategory;
    private Category mockCategory2;

    private final String ownerId = toString();

    @AfterEach
    void cleanup() {
        categoryRepository.deleteAll();
    }

    @BeforeEach
    void setup() {
        
        mockCategory = new Category(null, "Title 1", "Description 1", ownerId);
        mockCategory2 = new Category(null, "Title 2", "Description 2", ownerId);

        categoryRepository.save(mockCategory);
        categoryRepository.save(mockCategory2);
    }

    @Test
    void findAllByOwnerIdShouldReturnCategories() {
        
        List<Category> categories = categoryRepository.findAllByOwnerId(ownerId);

        assertAll(
                () -> assertEquals(2, categories.size()),
                () -> assertTrue(categories.contains(mockCategory)),
                () -> assertTrue(categories.contains(mockCategory2)));
    }
}