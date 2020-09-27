package com.desafio.desafio.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.desafio.desafio.domain.Car;
import com.desafio.desafio.domain.User;
import com.desafio.desafio.repositories.CarRepository;
import com.desafio.desafio.repositories.UserRepository;
import com.desafio.desafio.security.UserSpringSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private S3Service s3Service;

    public List<Car> findAllByUser(User user) {
        return carRepository.findAllByUser(user);
    }

    public Car findById(Integer id){
        Optional<Car> obj = carRepository.findById(id);
        return obj.orElse(null);
    }

    public Car findByPlate(String plate){
        Optional<Car> obj = carRepository.findByLicensePlate(plate);
        return obj.orElse(null);
    }

    public Car create(Car car) throws Exception {
        UserSpringSecurity userSS = UserSpringSecurityService.authenticated();
        if (userSS == null) {
            throw new Exception();
        }
        
        User user = userService.find(userSS.getId());
        car.setUser(user);
        return carRepository.save(car);
    }

    public Car createByUser(Car car) throws Exception {
        Car obj = findByPlate(car.getLicensePlate());
        if (obj == null) {
            return carRepository.save(car);
        }
        throw new Exception();
    }

    public void delete(Integer id) {
        findById(id);
        carRepository.deleteById(id);
	}

	public Car update(Car obj) throws Exception {
        findById(obj.getId());

        UserSpringSecurity userSS = UserSpringSecurityService.authenticated();
        if (userSS == null) {
            throw new Exception();
        }
        
        User user = userService.find(userSS.getId());
        obj.setUser(user);
		return carRepository.save(obj);
    }
    
    public URI uploadCarPicture(MultipartFile multipartFile, Integer id) throws Exception {

        UserSpringSecurity userSS = UserSpringSecurityService.authenticated();
        if (userSS == null) {
            throw new Exception();
        }
        URI uri =  s3Service.uploadFile(multipartFile, "/car");
        
        User user = userService.find(userSS.getId());
        for (Car car : user.getCars()) {
            if (car.getId() == id){
                car.setImageUrl(uri.toString());
                carRepository.save(car);
            }
        }

        return uri;
    }
    
}
