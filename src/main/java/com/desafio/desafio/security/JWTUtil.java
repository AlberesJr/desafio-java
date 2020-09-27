package com.desafio.desafio.security;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.desafio.desafio.domain.User;
import com.desafio.desafio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    
    @Autowired
    private HttpServletRequest req;

    public String generateToken(final String username){
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.getBytes())
            .compact();
    }

    public boolean isValidToken(final String token) {
        final Claims claims = getClaims(token, req);
        
        if (claims != null) {
            final String login = claims.getSubject();
            final Date expiration = claims.getExpiration();
            final Date now = new Date(System.currentTimeMillis());

            if (login != null && expiration != null && now.before(expiration)) {
                return true;
            }
        }
        return false;
    }

    public String getLogin(final String token) {
        final Claims claims = getClaims(token, req);
        
        if (claims != null) {
           return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(final String token, HttpServletRequest req) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (final ExpiredJwtException ex){
            System.out.println("Expired JWT token");
            req.setAttribute("expired", "Unauthorized - invalid session");
            return null;
        } catch(final Exception ex){
            return null;
        }
    }

    public User findUserByLogin(final String login){
        final User obj = service.findByLogin(login);

        final Calendar calendar = Calendar.getInstance();
        final Date now = calendar.getTime();
        obj.setLastLogin(new java.sql.Timestamp(now.getTime()));

        service.update(obj);
        return obj;
    }

    public void updateUserLastLogin(String login) {
        User user = findUserByLogin(login);

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        user.setLastLogin(new java.sql.Timestamp(now.getTime()));
        service.update(user);
    }
    
}
