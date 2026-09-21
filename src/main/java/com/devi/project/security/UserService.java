package com.devi.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devi.project.model.*;
import com.devi.project.repository.UserRepo;
@Service 
public class UserService implements UserDetailsService {//spring securtiy :UserDetailsService(interface)->userdetails loadbyname(un) :it gives userdetails from that username
 //impelementating this given method
 @Autowired 
 private UserRepo  up;
 @Override 
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
User user= up.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
      return org.springframework.security.core.userdetails.User//userdetails wants spring securtiy
        .withUsername(user.getUsername())//usename from db
        .password(user.getPassword())
        .roles(user.getRole())
        .build();//un,ps,rl->build userdetails
    }
}
