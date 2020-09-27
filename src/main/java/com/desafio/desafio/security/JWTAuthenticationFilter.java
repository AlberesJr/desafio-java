package com.desafio.desafio.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.desafio.desafio.domain.User;
import com.desafio.desafio.dto.CredetialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(final AuthenticationManager authenticationManager, final JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

        setFilterProcessesUrl("/api/signin");
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
            throws AuthenticationException {

        try {
            final CredetialsDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredetialsDTO.class);

            final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getLogin(), creds.getPassword());

            final Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res,
            final FilterChain filter, final Authentication auth) throws IOException, ServletException {

        final String login = ((UserSpringSecurity) auth.getPrincipal()).getUsername();
        final String token = jwtUtil.generateToken(login);
        res.addHeader("Authorization", "Bearer " + token);
        res.setContentType("application/json");
        jwtUtil.updateUserLastLogin(login);
        // User obj = jwtUtil.findUserByLogin(login);
        // String json = new Gson().toJson(obj);
        // System.out.println(json);
        // obj = new ObjectMapper().readValue(jsonSuccess(obj), User.class);
        // res.getWriter().append(json);
    }

    private String jsonSuccess(User obj){
        return "{\"id\": " + obj.getId() + ", "
          + "\"firstName\": " + obj.getFirstName() + ", "
          + "\"lastName\": " + obj.getLastName() + ", "
          + "\"email\": " + obj.getEmail() + ", "
          + "\"birthday\": " + obj.getBirthday() + ", "
          + "\"login\": " + obj.getLogin() + ", "
          + "\"phone\": " + obj.getPhone() + "}";
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception)
                throws IOException, ServletException {
            // if(exception.getClass().isAssignableFrom(UsernameNotFoundException.class){
            //     response.getWriter().app;
            // }
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }
        
        private String json() {
            return "{\"message\": \"Invalid login or password\", "
                + "\"errorCode\": 401}";
        }
    }
    
}
