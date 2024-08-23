package com.example.backend.repository;

import com.example.backend.document.Condition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends MongoRepository<Condition,String> {
    boolean existsByNormalizedName(String name);
    List<Condition> findAllByIdNotIn(List<String> ids);
}
