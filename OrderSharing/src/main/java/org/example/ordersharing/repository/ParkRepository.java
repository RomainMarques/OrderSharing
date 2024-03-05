package org.example.ordersharing.repository;

import org.example.ordersharing.model.Park;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkRepository extends MongoRepository<Park, String> {
    Park findByName(String Name);
}
