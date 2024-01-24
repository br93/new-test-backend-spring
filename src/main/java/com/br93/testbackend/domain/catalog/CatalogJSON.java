package com.br93.testbackend.data.catalog;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CatalogJSON implements Serializable {
    private String owner;
    private List<CatalogDTO> catalog;
}
