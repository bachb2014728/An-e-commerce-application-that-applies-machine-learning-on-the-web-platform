package com.example.backend.repository;

import com.example.backend.document.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends MongoRepository<Seller,String> {
    boolean existsByName(String name);
    boolean existsBySlug(String slug);
    List<Seller> findAllByUniqueIdentifierAndIdNotIn(boolean unique,List<String> ids);
    List<Seller> findAllByUniqueIdentifier(boolean unique);
}
