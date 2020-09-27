package com.desafio.desafio.dto;

import com.desafio.desafio.domain.Car;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    
}
