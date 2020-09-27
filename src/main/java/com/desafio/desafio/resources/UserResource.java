package com.desafio.desafio.resources;

import com.desafio.desafio.domain.Car;
import com.desafio.desafio.domain.User;
import com.desafio.desafio.dto.CarDTO;
import com.desafio.desafio.dto.UserDTO;
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

import java.util.Date;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
* Classe responsável pelo acesso de recursos de "User"
* 
* @author Alberes Jr
* @version 1.0.0
*/
@RestController
@RequestMapping(value = "/api")
@Api(tags = "Usuários")
public class UserResource {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CarService carService;

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
    public ResponseEntity<Object> createUser(@RequestBody User user) throws Exception {
        ErrorMessage er = new ErrorMessage();

        User obj = userService.findByEmail(user.getEmail());
        if (obj != null){
            er.setMessage("Email already exists");
            return new ResponseEntity<Object>(er, new HttpHeaders(), HttpStatus.valueOf(er.getErrorCode()));
        }
        obj = userService.findByLogin(user.getLogin());
        if (obj != null) {
            er.setMessage("Login already exists");
            return new ResponseEntity<Object>(er, new HttpHeaders(), HttpStatus.valueOf(er.getErrorCode()));
        }

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        user.setCreatedAt(new java.sql.Timestamp(now.getTime()));

        UserDTO userDto = new UserDTO(userService.create(user));
        List<CarDTO> carsDTOs = new ArrayList<>();
        final Pattern pattern = Pattern.compile("[aA-zZ]{3}-?[0-9]{4}");
        for (Car car : user.getCars()) {
            if (!pattern.matcher(car.getLicensePlate()).matches()) {
                er = new ErrorMessage("Invalid fields", 400);
                return new ResponseEntity<Object>(er, new HttpHeaders(), HttpStatus.valueOf(er.getErrorCode()));
            }
            car.setUser(user);
            carService.createByUser(car);
            carsDTOs.add(new CarDTO(car));
        }
        userDto.setCars(carsDTOs);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}")
    @ApiOperation(value = "Atualiza um usuário pelo id")
    public ResponseEntity<Object> update(@RequestBody User user, @PathVariable Integer id) {
        
        User obj = userService.findByEmail(user.getEmail());
        if (obj != null){
            ErrorMessage er = new ErrorMessage("Email already exists", 400);
            return new ResponseEntity<Object>(er, new HttpHeaders(), HttpStatus.valueOf(er.getErrorCode()));
        }
        obj = userService.findByLogin(user.getLogin());
        if (obj != null) {
            ErrorMessage er = new ErrorMessage("Login already exists", 400);
            er.setMessage("Login already exists");
            return new ResponseEntity<Object>(er, new HttpHeaders(), HttpStatus.valueOf(er.getErrorCode()));
        }
        
        user.setId(id);
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/users/{id}")
    @ApiOperation(tags = {"Usuários"}, value = "Remove um usuário pelo id")
    public ResponseEntity<User> delete(@PathVariable Integer id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/users/picture")
    @ApiOperation(tags = {"Usuários"}, value = "Faz o upload de imagem do usário")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile multipartFile)
            throws Exception {
        URI uri = userService.uploadUserPicture(multipartFile);
        return ResponseEntity.created(uri).build();
    }
    
}
