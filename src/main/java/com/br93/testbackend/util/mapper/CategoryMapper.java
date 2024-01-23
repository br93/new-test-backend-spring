package com.br93.testbackend.util.mapper;

import org.springframework.stereotype.Component;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.dto.CategoryDTO;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto) {
        return new Category(null, dto.getTitle(), dto.getDescription(), dto.getOwnerId());
    }

    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getTitle(), category.getDescription(), category.getOwnerId());
    }
}
