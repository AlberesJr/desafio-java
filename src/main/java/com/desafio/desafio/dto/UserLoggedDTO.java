package com.desafio.desafio.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.desafio.desafio.domain.Car;
import com.desafio.desafio.domain.User;

public class UserLoggedDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String login;
    private String phone;
    private String createdAt;
    private String lastLogin;

    private List<CarDTO> cars = new ArrayList<>();
    
    public UserLoggedDTO(User user) {
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        this.setBirthday(convertLocalDateToString(user.getBirthday()));
        this.setLogin(user.getLogin());
        this.setPhone(user.getPhone());
        this.setCreatedAt(convertDateToString(user.getCreatedAt()));
        this.setLastLogin(convertDateToString(user.getLastLogin()));
        this.setCars(fromDto(user.getCars()));
    }

    
    
    private String convertDateToString(Date date) {
        DateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simple.format(date);
    }

    private String convertLocalDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    private List<CarDTO> fromDto(List<Car> list) {
        List<CarDTO> cars = new ArrayList<>();
        for (Car car : list) {
            cars.add(new CarDTO(car));
        }
        return cars;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }
    
}
