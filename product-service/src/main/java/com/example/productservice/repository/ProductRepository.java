package com.example.productservice.repository;

import com.example.productservice.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{id:?0}")
    Optional<Product> getProductById(String id);

    @Query("{$or:[{name:?0},{price:?1}]}")
    List<Product> getProductsByNameOrPrice(String name, Float price);
}
