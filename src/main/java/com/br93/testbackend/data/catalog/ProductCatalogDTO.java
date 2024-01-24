package com.br93.testbackend.data.catalog;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCatalogDTO implements Serializable{

    private String title;
    private String description;
    private String price;
    
}
