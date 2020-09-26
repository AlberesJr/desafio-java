package com.desafio.desafio.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
    private String login;

    // @JsonIgnore
    private String password;
    private String phone;
    private Date createdAt;
    private Date lastLogin;

    // @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Car> cars = new ArrayList<>();
}
