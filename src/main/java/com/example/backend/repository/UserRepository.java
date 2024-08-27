package com.example.backend.repository;

import com.example.backend.document.Account;
import com.example.backend.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByAccount(Account account);
}
