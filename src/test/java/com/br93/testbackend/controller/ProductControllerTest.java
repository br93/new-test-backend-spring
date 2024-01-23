package com.br93.testbackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.Product;
import com.br93.testbackend.data.dto.ProductDTO;
import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.exception.CategoryNotFoundException;
import com.br93.testbackend.exception.ProductInvalidException;
import com.br93.testbackend.service.ProductService;
import com.br93.testbackend.util.Parser;
import com.br93.testbackend.util.mapper.ProductMapper;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    private Product mockProduct;
    private ProductDTO mockDTO;

    private Category mockCategory;

    private final String title = "title";
    private final String description = "description";
    private final BigDecimal price = BigDecimal.ONE;
    private final String ownerId = "ownerId";
    private final String categoryId = "categoryId";
   

    @BeforeEach
    void init() {

        mockCategory = new Category(categoryId, title, description, ownerId);
        mockProduct = new Product(null, title, description, price, mockCategory, ownerId);
        
        mockDTO = new ProductDTO(title, description, price, categoryId, ownerId);

    }

    @Test
    void shouldPostProductStatus200_Success() throws Exception {

        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(mockProduct);
        when(productService.createProduct(any(Product.class))).thenReturn(mockProduct);
        when(productMapper.toDTO(any(Product.class))).thenReturn(mockDTO);

        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isCreated(), jsonPath("$.price").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostProductStatus400_InvalidProduct() throws Exception {

        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(mockProduct);
        when(productService.createProduct(any(Product.class))).thenThrow(new ProductInvalidException("Product invalid"));
                
        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostProductStatus400_InvalidCategory() throws Exception {

        when(productMapper.toEntity(any(ProductDTO.class))).thenThrow(new CategoryInvalidException("Category invalid"));
                       
        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostProductStatus400_IlegalArgument() throws Exception {

        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(mockProduct);
        when(productService.createProduct(any(Product.class))).thenThrow(new IllegalArgumentException());
                       
        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isBadRequest(), content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostProductStatus404_CategoryNotFound() throws Exception {

        when(productMapper.toEntity(any(ProductDTO.class))).thenThrow(new CategoryNotFoundException("Category not found"));
                       
        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Parser.parse(mockDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound(), content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andDo(MockMvcResultHandlers.print());
    }
}