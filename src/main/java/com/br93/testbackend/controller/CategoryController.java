package com.br93.testbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.dto.CategoryDTO;
import com.br93.testbackend.service.CategoryService;
import com.br93.testbackend.util.mapper.CategoryMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO request){
        
        Category category = this.categoryMapper.toEntity(request);
        Category created = this.categoryService.createCategory(category);
        CategoryDTO response = this.categoryMapper.toDTO(created);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
