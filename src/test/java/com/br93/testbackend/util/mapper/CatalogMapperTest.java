package com.br93.testbackend.util.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.Product;
import com.br93.testbackend.data.catalog.CatalogDTO;
import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.data.catalog.ProductCatalogDTO;
import com.br93.testbackend.service.CategoryService;
import com.br93.testbackend.service.ProductService;

@SpringBootTest
class CatalogMapperTest {
    
    @Autowired
    private CatalogMapper catalogMapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    private Product mockProduct;
    private List<Product> listProduct;
    private Category mockCategory;
    private List<Category> listCategory;

    private final String title = "title";
    private final String description = "description";
    private final String ownerId = UUID.randomUUID().toString();
    private final BigDecimal price = BigDecimal.ONE;

    private ProductCatalogDTO productCatalogDTO;
    private CatalogDTO catalogDTO;
    private CatalogJSON catalogJSON;

    @BeforeEach
    void setup() {
        mockCategory = new Category(UUID.randomUUID().toString(), title, description, ownerId);
        mockProduct = new Product(UUID.randomUUID().toString(), title, description, price, mockCategory, ownerId);

        listProduct = List.of(mockProduct);
        listCategory = List.of(mockCategory);

        productCatalogDTO = new ProductCatalogDTO(title, description, price.toString());
        catalogDTO = new CatalogDTO(title, description, List.of(productCatalogDTO));
        catalogJSON = new CatalogJSON(ownerId, List.of(catalogDTO));
    }

    @Test
    void toProductCatalogDTOShouldReturnProductCatalogDTOFromProduct() {
        
        var dto = this.catalogMapper.toProductCatalogDTO(mockProduct);
        assertEquals(productCatalogDTO, dto);
    }

    @Test
    void toCatalogDTOShouldReturnCatalogDTOFromCategory() {
        when(productService.findAllProductsByCategoryId(anyString())).thenReturn(listProduct);

        var dto = this.catalogMapper.toCatalogDTO(mockCategory);
        assertEquals(catalogDTO, dto);
    }

    @Test
    void toCatalogJSONShouldReturnCatalogJSONFromOwnerId() {
        when(categoryService.findAllByOwnerId(anyString())).thenReturn(listCategory);
        when(productService.findAllProductsByCategoryId(anyString())).thenReturn(listProduct);

        var dto = this.catalogMapper.toCatalogJSON(ownerId);
        assertEquals(catalogJSON, dto);
    }
    
}
