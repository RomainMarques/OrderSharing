package org.example.ordersharing.repository;

import org.example.ordersharing.model.User;
import org.springframework.data.mongodb.repository.*;
public interface UserRepository extends MongoRepository<User, String> {
}