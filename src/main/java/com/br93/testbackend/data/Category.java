package com.br93.testbackend.data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "categories")
public class Category {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String title;
    private String description;
    private String ownerId;
    
}
