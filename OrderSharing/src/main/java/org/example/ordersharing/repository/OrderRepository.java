package org.example.ordersharing.repository;

import org.example.ordersharing.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByParkName(String parkName);
}
