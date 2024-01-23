package com.br93.testbackend.util.mapper;

import org.springframework.stereotype.Component;

import com.br93.testbackend.data.Product;
import com.br93.testbackend.data.dto.ProductDTO;
import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.exception.CategoryNotFoundException;
import com.br93.testbackend.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryService categoryService;

    public Product toEntity(ProductDTO dto) {

        if (dto.getCategory() == null)
            throw new CategoryInvalidException("Category invalid");

        var category = this.categoryService.findCategoryById(dto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return new Product(null, dto.getTitle(), dto.getDescription(), dto.getPrice(), category,
                dto.getOwnerId());
    }

    public ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getTitle(), product.getDescription(), product.getPrice(),
                product.getCategory().getTitle(), product.getOwnerId());
    }
}
