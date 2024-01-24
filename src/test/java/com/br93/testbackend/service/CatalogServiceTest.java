package com.br93.testbackend.service;

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

import com.br93.testbackend.data.catalog.CatalogDTO;
import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.data.catalog.ProductCatalogDTO;
import com.br93.testbackend.util.mapper.CatalogMapper;

@SpringBootTest
class CatalogServiceTest {
    
    @Autowired
    private CatalogService catalogService;

    @MockBean
    private CatalogMapper catalogMapper;

    private final String title = "title";
    private final String description = "description";
    private final String price = BigDecimal.ONE.toString();
    private final String ownerId = UUID.randomUUID().toString();

    private CatalogJSON catalogJSON;
    private List<CatalogDTO> listCatalogDTO;
    private List<ProductCatalogDTO> listProductCatalogDTO;

    @BeforeEach
    void setup() {
        listProductCatalogDTO = List.of(new ProductCatalogDTO(title, description, price));
        listCatalogDTO = List.of(new CatalogDTO(title, description, listProductCatalogDTO));
        catalogJSON = new CatalogJSON(ownerId, listCatalogDTO);
    }

    @Test
    void createCatalogShouldReturnCatalogDTO() {
        when(catalogMapper.toCatalogJSON(anyString())).thenReturn(catalogJSON);

        var actual = this.catalogService.createCatalog(ownerId);
        assertEquals(catalogJSON, actual);
    }
}
