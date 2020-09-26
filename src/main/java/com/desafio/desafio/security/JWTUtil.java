package com.desafio.desafio.security;

import java.util.Calendar;
import java.util.Date;

import com.desafio.desafio.domain.User;
import com.desafio.desafio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private UserService service;

    public String generateToken(String username){
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.getBytes())
            .compact();
    }

    public boolean isValidToken(String token) {
        Claims claims = getClaims(token);
        
        if (claims != null) {
            String login = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            if (login != null && expiration != null && now.before(expiration)) {
                return true;
            }
        }
        return false;
    }

    public String getLogin(String token) {
        Claims claims = getClaims(token);
        
        if (claims != null) {
           return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch(Exception ex){
            return null;
        }
    }

    public User findUserByLogin(String login){
        User obj = service.findByLogin(login);

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        obj.setLastLogin(new java.sql.Timestamp(now.getTime()));

        service.update(obj);
        return obj;
    }
    
}
