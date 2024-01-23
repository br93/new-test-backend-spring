package com.br93.testbackend.util.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.Product;
import com.br93.testbackend.data.dto.ProductDTO;
import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.exception.CategoryNotFoundException;
import com.br93.testbackend.service.CategoryService;

@SpringBootTest
class ProductMapperTest {
    
    @Autowired
    private ProductMapper productMapper;

    @MockBean
    private CategoryService categoryService;

    private ProductDTO mockDTO;
    private Product mockProduct;

    private Category mockCategory;

    private final String title = "title";
    private final String description = "description";
    private final BigDecimal price = BigDecimal.ONE;
    private final String categoryId = "categoryId";
    private final String ownerId = "ownerId";

    @BeforeEach()
    void setup(){
        
        mockDTO = new ProductDTO(title, description, price, categoryId, ownerId);
        mockCategory = new Category(categoryId, title, description, ownerId);
        mockProduct = new Product(null, title, description, price, mockCategory, ownerId);
    }

    @Test
    void toEntityShouldReturnProduct(){
        
        when(categoryService.findCategoryById(anyString())).thenReturn(Optional.of(mockCategory));

        Product product = productMapper.toEntity(mockDTO);
        assertEquals(mockDTO.getTitle(), product.getTitle());
    }

    @Test
    void toEntityShouldThrowCategoryNullExceptionIfCategoryNull(){
        
        mockDTO.setCategory(null);
        assertThrowsExactly(CategoryInvalidException.class, () -> productMapper.toEntity(mockDTO));
    }

    @Test
    void toEntityShouldThrowCategoryNotFoundExceptionIfCategoryNotFound(){
        
        mockDTO.setCategory(UUID.randomUUID().toString());
        assertThrowsExactly(CategoryNotFoundException.class, () -> productMapper.toEntity(mockDTO));
    }

    @Test
    void toDTOShouldReturnProductDTO() {
        ProductDTO productDTO = productMapper.toDTO(mockProduct);
        assertEquals(mockProduct.getCategory().getTitle(), productDTO.getCategory());
    }
}
