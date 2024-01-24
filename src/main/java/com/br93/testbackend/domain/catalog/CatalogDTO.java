package com.br93.testbackend.data.catalog;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CatalogDTO implements Serializable {

    @JsonProperty(value = "category_title")
    private String categoryTitle;

    @JsonProperty(value = "category_description")
    private String categoryDescription;
    
    private List<ProductCatalogDTO> itens;
    
}
