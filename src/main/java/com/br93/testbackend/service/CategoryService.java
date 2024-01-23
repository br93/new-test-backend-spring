package com.br93.testbackend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.repository.CategoryRepository;
import com.br93.testbackend.util.validation.CategoryValidation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryValidation categoryValidation;

    public Category createCategory(Category category) {

        if(!this.categoryValidation.isValidCategory(category)){
            throw new CategoryInvalidException("Category invalid");
        }

        return this.categoryRepository.save(category);
    }

    public Optional<Category> findCategoryById(String id) {
        return this.categoryRepository.findById(id);
    }
}
