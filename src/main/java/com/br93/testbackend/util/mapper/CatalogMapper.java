package com.br93.testbackend.util.mapper;

import org.springframework.stereotype.Component;

import com.br93.testbackend.data.Category;
import com.br93.testbackend.data.Product;
import com.br93.testbackend.data.catalog.CatalogDTO;
import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.data.catalog.ProductCatalogDTO;
import com.br93.testbackend.service.CategoryService;
import com.br93.testbackend.service.ProductService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CatalogMapper {
    
    private final ProductService productService;
    private final CategoryService categoryService;
    
    public ProductCatalogDTO toProductCatalogDTO (Product product){
        return new ProductCatalogDTO(product.getTitle(), product.getDescription(), product.getPrice().toString()); 
    }

    public CatalogDTO toCatalogDTO (Category category){

        var itens = productService.findAllProductsByCategoryId(category.getId());
        var list = itens.stream().map(this::toProductCatalogDTO).toList();

        return new CatalogDTO(category.getTitle(), category.getDescription(), list);    
    }

    public CatalogJSON toCatalogJSON (String ownerId){
        
        var categories = categoryService.findAllByOwnerId(ownerId);
        var list = categories.stream().map(this::toCatalogDTO).toList();

        return new CatalogJSON(ownerId, list);
    }
}