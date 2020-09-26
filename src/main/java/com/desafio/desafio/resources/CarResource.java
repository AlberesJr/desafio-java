package com.desafio.desafio.resources;

import java.util.List;

import com.desafio.desafio.domain.Car;
import com.desafio.desafio.domain.User;
import com.desafio.desafio.service.CarService;
import com.desafio.desafio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CarResource {
    
    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/cars")
    public ResponseEntity<List<Car>> findAllByUser(@RequestParam Integer userId){
        User user = userService.find(userId);
        List<Car> cars = carService.findAllByUser(user);
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping(value = "/cars/{id}")
    public ResponseEntity<Car> find(@PathVariable Integer id){
        Car obj = carService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car, @RequestParam String login) throws Exception {
        User user = userService.findByLogin(login);
        car.setUser(user);
        Car obj = carService.create(car);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping(value = "/cars/{id}")
    public ResponseEntity<Car> update(@RequestBody Car obj, @PathVariable Integer id) {
        obj.setId(id);
        obj = carService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/cars/{id}")
    public ResponseEntity<Car> delete(@PathVariable Integer id){
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
