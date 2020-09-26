package com.desafio.desafio.service;

import java.util.Optional;

import com.desafio.desafio.domain.User;
import com.desafio.desafio.repositories.UserRepository;
import com.desafio.desafio.security.UserSpringSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = repo.findByLogin(login);

        if(user == null){
            throw new UsernameNotFoundException(login);
        }
        return new UserSpringSecurity(
            user.get().getId(),
            user.get().getLogin(),
            user.get().getPassword()
        );
    }
    
}
