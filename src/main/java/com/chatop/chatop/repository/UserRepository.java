package com.chatop.chatop.repository;

import com.chatop.chatop.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;



public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
