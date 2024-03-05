package org.example.ordersharing.repository;

import org.example.ordersharing.model.IndividualOrder;
import org.example.ordersharing.model.SharedOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IndividualOrderRepository extends MongoRepository<IndividualOrder, String> {
}
