package com.example.backend.repository;

import com.example.backend.document.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(String name);
    boolean existsRoleByName(String name);
}
