package com.br93.testbackend.util.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.Product;

@SpringBootTest
class ProductValidationTest {

    @Autowired
    private ProductValidation productValidation;
    
    private Product mockProduct;
    private Category mockCategory;
    
    private final String ownerId =  "ownerId";
    private final String title = "title";
    private final String description = "description";

    @BeforeEach
    void init(){
        mockProduct = new Product();
        
        mockCategory = new Category();
        mockCategory.setDescription(description);
        mockCategory.setOwnerId(ownerId);
        mockCategory.setTitle(title);
    }

    @Test
    void isValidProductShouldReturnFalseIfSomeFieldNull(){
        assertEquals(false, this.productValidation.isValidProduct(mockProduct));
    }

    @Test
    void isValidProductShouldReturnTrueIfNoneFieldNull(){
        
        mockProduct.setCategory(mockCategory);
        mockProduct.setDescription(description);
        mockProduct.setOwnerId(ownerId);
        mockProduct.setPrice(BigDecimal.ONE);
        mockProduct.setTitle(title);

        assertEquals(true, this.productValidation.isValidProduct(mockProduct));
    }
    
}
