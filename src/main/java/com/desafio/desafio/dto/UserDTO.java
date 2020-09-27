package com.desafio.desafio.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.desafio.desafio.domain.Car;
import com.desafio.desafio.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String login;
    private String password;
    private String phone;

    private List<Car> cars = new ArrayList<>();

    public UserDTO(User user) {
        this.setId(user.getId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        this.setBirthday(user.getBirthday());
        this.setLogin(user.getLogin());
        this.setPassword(user.getPassword());
        this.setPhone(user.getPhone());
        this.setCars(user.getCars());
    }
    
    

}
