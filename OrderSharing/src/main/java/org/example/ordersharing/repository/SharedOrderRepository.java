package org.example.ordersharing.repository;

import org.example.ordersharing.model.SharedOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SharedOrderRepository extends MongoRepository<SharedOrder, String> {
    List<SharedOrder> findByParkName(String parkName);
    List<SharedOrder> findByAlleyNumber(String alleyNumber);
}
