package com.desafio.desafio.repositories;

import java.util.Optional;

import com.desafio.desafio.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);

    @Transactional(readOnly = true)
    Optional<User> findByLogin(String login);
}
