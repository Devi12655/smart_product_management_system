package com.devi.project.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component 
public class JwtFilter extends OncePerRequestFilter {
   private final JwtService jwtService;
private final UserService userService;

public JwtFilter(JwtService jwtService, UserService userService) {
    this.jwtService = jwtService;
    this.userService = userService;
}
    @Override
    protected void doFilterInternal(//validates the request
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
                String authHeader = request.getHeader("Authorization");//authorization  header name
                if (authHeader != null && authHeader.startsWith("Bearer ")) {//bearer:authentication schemeWhoever bears (possesses) this token can present it as their authentication credential.
              try{
                    String jwt = authHeader.substring(7);//bearer and space :7 remove start index
                          String username = jwtService.extractUsername(jwt);
                          UserDetails userDetails = userService.loadUserByUsername(username);//user recordor not found exception
        UsernamePasswordAuthenticationToken authentication =//create authentication and gives or (put at springcontext)
        new UsernamePasswordAuthenticationToken(//Authentication object
                username,//already jwt->mana server sign verfied so username and vadiki permission->obj
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder//springsecurtiy knows user authenticaed devi = authenticated USER//has.role()
        .getContext()
        .setAuthentication(authentication);
    }
    catch(Exception e){
        System.out.println(e);
    }

    }
    filterChain.doFilter(request, response);// Continue to next filter(next request)Next Security Filter ->authentication null stop
}
}