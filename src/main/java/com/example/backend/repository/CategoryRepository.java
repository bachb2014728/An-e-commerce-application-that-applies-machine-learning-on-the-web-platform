package com.example.backend.repository;

import com.example.backend.document.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
    boolean existsCategoryByName(String name);
    boolean existsCategoryBySlug(String slug);
    boolean existsCategoryById(String id);
    List<Category> findAllByParentId(String level);
}
