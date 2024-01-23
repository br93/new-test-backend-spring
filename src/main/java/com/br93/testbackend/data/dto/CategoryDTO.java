package com.br93.testbackend.data.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    
    @NotBlank(message = "Title is mandatory")
    private String title;
    
    @NotBlank(message = "Title is mandatory")
    private String description;
    
    @NotBlank(message  = "Owner id is mandatory")
    @JsonAlias(value = "owner")
    @JsonProperty(value = "owner")
    private String ownerId;
}
