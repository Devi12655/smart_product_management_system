package com.devi.project.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devi.project.model.User;
import com.devi.project.repository.UserRepo;

@Configuration
public class DataSeeder {//intial adimin creation(springboot start automatically created in db if not)

    @Bean
    CommandLineRunner seedUsers(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepo.findByUsername("admin").isEmpty()) {

                User admin = new User();

                admin.setUsername("admin");

                admin.setPassword(
                    passwordEncoder.encode("admin123")
                );

                admin.setRole("ADMIN");

                userRepo.save(admin);
            }
        };
    }
}