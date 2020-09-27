package com.desafio.desafio.dto;

import com.desafio.desafio.domain.Car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {

    private int year;
    private String licensePlate;
    private String model;
    private String color;
    
    public CarDTO(Car car) {
        this.setYear(car.getYear());
        this.setLicensePlate(car.getLicensePlate());
        this.setModel(car.getModel());
        this.setColor(car.getColor());
    }
}
