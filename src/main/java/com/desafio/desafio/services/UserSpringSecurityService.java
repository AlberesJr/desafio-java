package com.desafio.desafio.services;

import com.desafio.desafio.security.UserSpringSecurity;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserSpringSecurityService {
    public static UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
