package com.br93.testbackend.data.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Price is mandatory")
    private BigDecimal price;
    
    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotBlank(message = "Owned id is mandatory")
    @JsonAlias(value = "owner")
    @JsonProperty(value = "owner")
    private String ownerId;    
}
