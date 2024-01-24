package com.br93.testbackend.util;

import com.br93.testbackend.data.catalog.CatalogJSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {

    public static String parse(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static String parsePretty(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static CatalogJSON toCatalog(String string) throws JsonProcessingException {
        return new ObjectMapper().readValue(string, CatalogJSON.class);
    }
    
}
