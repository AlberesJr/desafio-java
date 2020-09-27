package com.desafio.desafio.resources;

import java.net.URI;
import java.util.List;

import com.desafio.desafio.domain.Car;
import com.desafio.desafio.domain.User;
import com.desafio.desafio.dto.CarDTO;
import com.desafio.desafio.exceptions.ErrorMessage;
import com.desafio.desafio.services.CarService;
import com.desafio.desafio.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* Classe responsável pelo acesso de recursos de "Car"
* 
* @author Alberes Jr
* @version 1.0.0
*/
@RestController
@RequestMapping(value = "/api")
@Api(tags = {"Carros"})
public class CarResource {
    
    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/cars")
    @ApiOperation(tags = {"Carros"}, value = "Lista todos os carros do usuário logado")
    public ResponseEntity<List<Car>> findAllByUser(@RequestParam Integer userId){
        User user = userService.find(userId);
        List<Car> cars = carService.findAllByUser(user);
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping(value = "/cars/{id}")
    @ApiOperation(tags = {"Carros"}, value = "Busca um carro do usuário logado pelo id")
    public ResponseEntity<Car> find(@PathVariable Integer id){
        Car obj = carService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/cars")
    @ApiOperation(tags = {"Carros"}, value = "Cadastra um novo carro para o usuário logado")
    public ResponseEntity<Object> createCar(@RequestBody Car car) throws Exception {
        Car obj = carService.findByPlate(car.getLicensePlate());

        if (obj != null) {
            ErrorMessage er = new ErrorMessage("License plate already exists", 400);
            return new ResponseEntity<Object>(er, new HttpHeaders(), HttpStatus.valueOf(er.getErrorCode()));
        }
        
        obj = carService.create(car);
        CarDTO carDto = new CarDTO(obj);
        return new ResponseEntity<>(carDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/cars/{id}")
    @ApiOperation(tags = {"Carros"}, value = "Atualiza um carro do usuário logado pelo id")
    public ResponseEntity<Object> update(@RequestBody Car car, @PathVariable Integer id) throws Exception {

        Car obj = carService.findByPlate(car.getLicensePlate());

        if (obj != null) {
            ErrorMessage er = new ErrorMessage("License plate already exists", 400);
            return new ResponseEntity<Object>(er, new HttpHeaders(), HttpStatus.valueOf(er.getErrorCode()));
        }
        car.setId(id);
        carService.update(car);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/cars/{id}")
    @ApiOperation(tags = {"Carros"}, value = "Remove um carro do usuário logado pelo id")
    public ResponseEntity<Car> delete(@PathVariable Integer id){
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/cars/picture/{id}")
    @ApiOperation(tags = {"Carros"}, value = "Faz o upload de imagem do carro")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile multipartFile, @PathVariable Integer id)
            throws Exception {
        URI uri = carService.uploadCarPicture(multipartFile, id);
        return ResponseEntity.created(uri).build();
    }
}
