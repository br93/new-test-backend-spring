package com.br93.testbackend.util.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.dto.CategoryDTO;

@SpringBootTest
class CategoryMapperTest {
    
    @Autowired
    private CategoryMapper categoryMapper;

    private CategoryDTO mockDTO;
    private Category mockCategory;

    private final String title = "title";
    private final String description = "description";
    private final String ownerId = "ownerId";

    @BeforeEach
    void setup() {
        mockDTO = new CategoryDTO(title, description, ownerId);
        mockCategory = new Category(null, title, description, ownerId);

    }

    @Test
    void toEntityShouldReturnCategory(){
        Category category = categoryMapper.toEntity(mockDTO);
        assertEquals(mockCategory.getTitle(), category.getTitle());
    }

    @Test
    void toDTOShouldReturnCategoryDTO() {
        CategoryDTO categoryDTO = categoryMapper.toDTO(mockCategory);
        assertEquals(mockDTO.getTitle(), categoryDTO.getTitle());
    }
}
