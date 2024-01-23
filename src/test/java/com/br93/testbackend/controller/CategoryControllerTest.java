package com.br93.testbackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private CategoryDTO mockDTO;

    private final String description = "description";
    private final String title = "title";
    private final String ownerId = "ownerId";

    @BeforeEach
    void setup() {
        mockDTO = new CategoryDTO(title, description, ownerId);
        mockCategory = new Category(null, title, description, ownerId);
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
}