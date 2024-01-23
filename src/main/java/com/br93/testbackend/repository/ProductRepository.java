package com.br93.testbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.br93.testbackend.data.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
}
