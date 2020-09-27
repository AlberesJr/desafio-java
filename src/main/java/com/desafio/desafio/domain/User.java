package com.desafio.desafio.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.Getter;
import lombok.Setter;

/**
* Classe de objetos do tipo "User"
* 
* @author Alberes Jr
* @version 1.0
*/

@Entity
@Getter
@Setter
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private LocalDate birthday;

    @Column(unique = true)
    private String login;
    private String password;
    private String phone;
    private Date createdAt;
    private Date lastLogin;
    private String imageUrl;
    @OneToMany(mappedBy = "user")
    private List<Car> cars = new ArrayList<>();
}
