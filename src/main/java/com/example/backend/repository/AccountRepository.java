package com.example.backend.repository;

import com.example.backend.document.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account,String> {
    Optional<Account> findAccountByEmail(String email);
    boolean existsAccountByEmail(String email);
}
