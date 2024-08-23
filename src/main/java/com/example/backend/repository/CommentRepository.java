package com.example.backend.repository;

import com.example.backend.document.Comment;
import com.example.backend.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {
    List<Comment> findAllByProduct(Product product);
}
