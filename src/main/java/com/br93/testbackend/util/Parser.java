package com.br93.testbackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {

    public static String parse(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
    
}
