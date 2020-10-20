package com.example.Authentication.repository;

import com.example.Authentication.models.ERole;
import com.example.Authentication.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
        Optional<Role> findByName(ERole name);
}
