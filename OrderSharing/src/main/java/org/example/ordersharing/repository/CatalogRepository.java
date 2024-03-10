package org.example.ordersharing.repository;

import org.example.ordersharing.model.Catalog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatalogRepository extends MongoRepository<Catalog, String> {
    Catalog findByQRCode(String QRCode);
}
