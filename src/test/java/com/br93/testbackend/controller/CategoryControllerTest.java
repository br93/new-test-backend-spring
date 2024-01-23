package com.br93.testbackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.dto.CategoryDTO;
import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.exception.CategoryNotFoundException;
import com.br93.testbackend.service.CategoryService;
import com.br93.testbackend.util.Parser;
import com.br93.testbackend.util.mapper.CategoryMapper;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    private Category mockCategory;
    private Category mockUpdated;
    private CategoryDTO mockDTO;
    private CategoryDTO mockUpdatedDTO;

    private final String description = "description";
    private final String title = "title";
    private final String ownerId = "ownerId";

    @BeforeEach
    void setup() {
        mockDTO = new CategoryDTO(title, description, ownerId);
        
        mockUpdatedDTO = mockDTO;
        mockUpdatedDTO.setTitle("title2");
        
        mockCategory = new Category(null, title, description, ownerId);
        mockUpdated = mockCategory;
        mockUpdated.setTitle("title2");
    }


@Test
    void shouldPostCategoryStatus200_Success() throws Exception {

        when(categoryMapper.toEntity(any(CategoryDTO.class))).thenReturn(mockCategory);
        when(categoryService.createCategory(any(Category.class))).thenReturn(mockCategory);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(mockDTO);

        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isCreated(), jsonPath("$.title").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostCategoryStatus400_InvalidCategory() throws Exception {

        when(categoryMapper.toEntity(any(CategoryDTO.class))).thenReturn(mockCategory);
        when(categoryService.createCategory(any(Category.class))).thenThrow(new CategoryInvalidException("Category invalid"));
    
        mockMvc.perform(post("/api/v1/category")
                .content(Parser.parse(mockDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void showPutCategoryStatus200_Success() throws Exception {

        when(categoryMapper.toEntity(any(CategoryDTO.class))).thenReturn(mockUpdated);
        when(categoryService.updateCategory(anyString(), any(Category.class))).thenReturn(mockUpdated);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(mockUpdatedDTO);

        String id = UUID.randomUUID().toString();
       
        mockMvc.perform(put("/api/v1/category/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockUpdatedDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), jsonPath("$.title").value("title2"));
    }

    @Test
    void shouldPutCategoryStatus404_CategoryNotFound() throws Exception {

        when(categoryMapper.toEntity(any(CategoryDTO.class))).thenReturn(mockUpdated);
        when(categoryService.updateCategory(anyString(), any(Category.class))).thenThrow(new CategoryNotFoundException("Category not found"));
        
        String id = UUID.randomUUID().toString();

        mockMvc.perform(put("/api/v1/category/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockUpdatedDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound(), content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void shouldPutCategoryStatus400_InvalidCategory() throws Exception {

        when(categoryMapper.toEntity(any(CategoryDTO.class))).thenReturn(mockUpdated);
        when(categoryService.updateCategory(anyString(), any(Category.class))).thenThrow(new CategoryInvalidException("Category invalid"));
              
        String id = UUID.randomUUID().toString();
       
        mockMvc.perform(put("/api/v1/category/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockUpdatedDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void shouldDeleteCategoryStatus204_Success() throws Exception {
        
        String id = UUID.randomUUID().toString();

        mockMvc.perform(delete("/api/v1/category/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}