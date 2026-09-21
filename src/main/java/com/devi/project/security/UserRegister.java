package com.devi.project.security;
import org.springframework.stereotype.Service;

import com.devi.project.repository.*;
import com.devi.project.exception.UserAlreadyExit;
import com.devi.project.model.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service 
public class UserRegister {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserRegister(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {//true or flase->optional value is there or not
        throw new UserAlreadyExit("Username already exists");
    }

        user.setRole("USER");
  
        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );

        return userRepo.save(user);
    }
}