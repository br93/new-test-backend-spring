package com.br93.testbackend.util.validation;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.br93.testbackend.data.Product;

@Component
public class ProductValidation {

    public boolean isValidProduct(Product product) {

        return Stream.of(product.getCategory(), product.getDescription(), product.getOwnerId(), product.getPrice(), product.getTitle())
                .noneMatch(Objects::isNull);
    }
    
}
