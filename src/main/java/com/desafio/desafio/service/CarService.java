package com.desafio.desafio.service;

import java.util.List;
import java.util.Optional;

import com.desafio.desafio.domain.Car;
import com.desafio.desafio.domain.User;
import com.desafio.desafio.repositories.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAllByUser(User user) {
        return carRepository.findAllByUser(user);
    }

    public Car findById(Integer id){
        Optional<Car> obj = carRepository.findById(id);
        return obj.orElse(null);
    }

    public Car findByPlate(String plate){
        Optional<Car> obj = carRepository.findByLicensePlate(plate);
        return obj.orElse(null);
    }

    public Car create(Car car) throws Exception {
        Car obj = findByPlate(car.getLicensePlate());
        if (obj == null) {
            return carRepository.save(car);
        }
        throw new Exception();
    }

    public void delete(Integer id) {
        findById(id);
        carRepository.deleteById(id);
	}

	public Car update(Car obj) {
        findById(obj.getId());
		return carRepository.save(obj);
	}
    
}
