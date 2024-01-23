package com.br93.testbackend.util.validation;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.br93.testbackend.data.Category;

@Component
public class CategoryValidation {

    public boolean isValidCategory(Category category) {

        return Stream.of(category.getDescription(), category.getOwnerId(), category.getTitle())
                .noneMatch(Objects::isNull);
    }

}
