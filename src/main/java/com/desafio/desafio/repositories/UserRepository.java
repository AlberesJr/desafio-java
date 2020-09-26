package com.desafio.desafio.repositories;

import java.util.Optional;

import com.desafio.desafio.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);
}
