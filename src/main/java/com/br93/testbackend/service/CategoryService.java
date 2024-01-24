package com.br93.testbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.exception.CategoryNotFoundException;
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

     public Category updateCategory(String id, Category newCategory) {
        var category = this.findCategoryById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (!newCategory.getTitle().isEmpty())
            category.setTitle(newCategory.getTitle());
        if (!newCategory.getDescription().isEmpty())
            category.setDescription(newCategory.getDescription());

        return this.createCategory(category);
    }

    public void deleteCategory(String id) {
        this.categoryRepository.deleteById(id);
    }

    public List<Category> findAllByOwnerId(String ownerId) {
        return this.categoryRepository.findAllByOwnerId(ownerId);
    }
}
