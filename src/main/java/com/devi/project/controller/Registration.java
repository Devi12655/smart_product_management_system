package com.devi.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.devi.project.model.*;
import com.devi.project.security.UserRegister;


@RestController 
public class Registration {
   @Autowired 
   private UserRegister us;
   @PostMapping ("/register")
public ResponseEntity<String>CreateUser(@RequestBody  User user){
    us.createUser(user);
    return new ResponseEntity<>("succcesfully registered",HttpStatus.ACCEPTED);
} 
    
}
