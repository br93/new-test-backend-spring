package com.br93.testbackend.service;

import org.springframework.stereotype.Service;

import com.br93.testbackend.data.catalog.CatalogJSON;
import com.br93.testbackend.util.mapper.CatalogMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogService {
    
    private final CatalogMapper catalogMapper;
    
    public CatalogJSON createCatalog(String ownerId){
        return this.catalogMapper.toCatalogJSON(ownerId);
    }

}
