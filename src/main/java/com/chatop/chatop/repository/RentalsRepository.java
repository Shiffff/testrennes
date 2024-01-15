package com.chatop.chatop.repository;

import com.chatop.chatop.entity.Rentals;
import com.chatop.chatop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalsRepository extends JpaRepository<Rentals, Long> {
    // Ajoutez ici des méthodes personnalisées si nécessaire


}
