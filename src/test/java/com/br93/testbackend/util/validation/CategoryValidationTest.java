package com.br93.testbackend.util.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br93.testbackend.data.Category;

@SpringBootTest
class CategoryValidationTest {
    
    @Autowired
    private CategoryValidation categoryValidation;

    private Category mockCategory;

    @BeforeEach
    void setup() {
        mockCategory = new Category();
    }

    @Test
    void isValidCategoryShouldReturnFalseIfSomeFieldNull(){
        assertEquals(false, this.categoryValidation.isValidCategory(mockCategory));
    }

    @Test
    void isValidCategoryShouldReturnTrueIfNoneFieldNull(){
        mockCategory.setDescription("description");
        mockCategory.setOwnerId("ownerId");
        mockCategory.setTitle("title");

        assertEquals(true, this.categoryValidation.isValidCategory(mockCategory));
    }
}
