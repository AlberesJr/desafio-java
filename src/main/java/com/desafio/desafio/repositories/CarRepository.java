package com.desafio.desafio.repositories;

import java.util.List;
import java.util.Optional;

import com.desafio.desafio.domain.Car;
import com.desafio.desafio.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
    
    Optional<Car> findByLicensePlate(String plate);
    List<Car> findAllByUser(User user);
}
