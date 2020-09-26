package com.desafio.desafio.service;

import java.util.List;
import java.util.Optional;

import com.desafio.desafio.domain.User;
import com.desafio.desafio.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder pe;
    
    @Autowired
    private UserRepository repo;

    public List<User> findAll() {
        return repo.findAll();
    }

    public User find(Integer id){
        Optional<User> obj = repo.findById(id);
        return obj.orElse(null);
    }

    public User create(User user) throws Exception {
        User obj = findByEmail(user.getEmail());
        if (obj == null) {
            user.setPassword(pe.encode(user.getPassword()));
            return repo.save(user);
        }
        throw new Exception();
    }

    public User findByEmail(String email) {
        Optional<User> obj = repo.findByEmail(email);
        return obj.orElse(null);
    }

    public User findByLogin(String login) {
        Optional<User> obj = repo.findByLogin(login);
        return obj.orElse(null);
    }

	public void delete(Integer id) {
        find(id);
        repo.deleteById(id);
	}

	public User update(User obj) {
        find(obj.getId());
		return repo.save(obj);
	}
}
