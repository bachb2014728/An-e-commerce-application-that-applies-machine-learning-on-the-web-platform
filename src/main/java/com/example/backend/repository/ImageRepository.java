package com.example.backend.repository;

import com.example.backend.document.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends MongoRepository<Image,String> {
    boolean existsByName(String name);
    List<Image> findByIdIn(List<String> ids);
}
