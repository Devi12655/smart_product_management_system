package com.devi.project.controller;

import com.devi.project.model.User;
import com.devi.project.security.JwtService;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {//custom jwt lo login controller(flow jwt generate)
    // authenticate
    // generate JWT
    // return token

    private final AuthenticationManager authenticationManager;//beans 
    private final JwtService jwtService;
//authentictation manager is an spring security :1 component
    public LoginController(AuthenticationManager authenticationManager,
                           JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

   @PostMapping("/login")
public ResponseEntity<String> login(@RequestBody User user) {

    try {// internally wrong అయితే method stop and exception raise
        //vaid or not user and password->springsecurity : userservicedetails
        Authentication authentication =
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
                )
            );

        String token = jwtService.generateToken(user.getUsername());//not executed

        return ResponseEntity.ok(token);

    } catch (BadCredentialsException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
    }
}
}