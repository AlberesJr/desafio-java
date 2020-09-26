package com.desafio.desafio.resources;

import com.desafio.desafio.domain.User;
import com.desafio.desafio.dto.UserDTO;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(tags = "Usuários")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/me")
    @ApiOperation(tags = {"Usuários"}, value = "Retorna informações do usuário logado")
    public ResponseEntity<User> findLoggedUser(@RequestParam String login){
        User obj = userService.findByLogin(login);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/users/{id}")
    @ApiOperation(tags = {"Usuários"}, value = "Busca um usuário pelo id")
    public ResponseEntity<User> find(@PathVariable Integer id){
        User obj = userService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/users")
    @ApiOperation(tags = {"Usuários"}, value = "Lista todos os usuários")
    public ResponseEntity<List<User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping(value = "/users")
    @ApiOperation(tags = {"Usuários"}, value = "Cadatra um novo usuário")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) throws Exception {

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        user.setCreatedAt(new java.sql.Timestamp(now.getTime()));

        UserDTO userDto = new UserDTO(userService.create(user));
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}")
    @ApiOperation(value = "Atualiza um usuário pelo id")
    public ResponseEntity<User> update(@RequestBody User obj, @PathVariable Integer id) {
        obj.setId(id);
        userService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/users/{id}")
    @ApiOperation(tags = {"Usuários"}, value = "Remove um usuário pelo id")
    public ResponseEntity<User> delete(@PathVariable Integer id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
