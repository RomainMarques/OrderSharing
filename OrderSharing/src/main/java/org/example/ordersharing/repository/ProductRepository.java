package org.example.ordersharing.repository;

import org.example.ordersharing.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByParkName(String ParkName);
}
