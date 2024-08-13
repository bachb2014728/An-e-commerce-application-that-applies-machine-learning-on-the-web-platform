package com.example.backend.repository;

import com.example.backend.document.Manufacturer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends MongoRepository<Manufacturer,String> {
    boolean existsManufacturerByName(String name);
    boolean existsManufacturerBySlug(String slug);
    List<Manufacturer> findAllByIdNotIn(List<String> ids);
}
