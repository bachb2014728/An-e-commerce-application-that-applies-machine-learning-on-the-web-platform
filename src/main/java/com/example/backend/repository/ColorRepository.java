package com.example.backend.repository;

import com.example.backend.document.Color;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends MongoRepository<Color,String> {
    boolean existsByNormalizedName(String name);
    List<Color> findAllByIdNotIn(List<String> ids);
    List<Color> findByIdIn(List<String> ids);
}
