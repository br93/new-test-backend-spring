package com.br93.testbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.repository.CategoryRepository;
import com.br93.testbackend.util.validation.CategoryValidation;

@SpringBootTest
class CategoryServiceTest {
    
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryValidation categoryValidation;

    @MockBean
    private CategoryRepository categoryRepository;

    private Category mockCategory;

    @BeforeEach
    void setup() {
        mockCategory = new Category(null, "title", "description", "ownerId");
    }

    @Test
    void createCategoryShouldReturnNewCategory() {
        when(categoryValidation.isValidCategory(any(Category.class))).thenReturn(true);
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);

        Category newCategory = categoryService.createCategory(mockCategory);

        assertEquals(mockCategory.getTitle(), newCategory.getTitle());
    }

    @Test
    void createCategoryShouldThrowCategoryInvalidExceptionIfCategoryInvalid() {

        when(categoryValidation.isValidCategory(any(Category.class))).thenReturn(false);
        assertThrowsExactly(CategoryInvalidException.class, () -> categoryService.createCategory(mockCategory));
    }

    @Test
    void findCategoryByIdShouldReturnOptionalOfCategory() {
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(mockCategory));

        Optional<Category> optional = categoryService.findCategoryById(UUID.randomUUID().toString());

        assertEquals(Optional.of(mockCategory), optional);
    }
}
