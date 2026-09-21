package com.devi.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }//matches(enteredPassword, storedHash)
@Bean
public AuthenticationManager authenticationManager(
        AuthenticationConfiguration configuration)
        throws Exception {

    return configuration.getAuthenticationManager();
}
    @Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtFilter jwtFilter)
        throws Exception {            

        http//define authorization rules
          .csrf(csrf -> csrf.disable()) 
          .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//stateless:don't use an HTTP session to remember the user's authentication.
)
            .authorizeHttpRequests(auth -> auth
                 .requestMatchers("/register").permitAll()
                 .requestMatchers("/login").permitAll()//anyone can register ,login(securtiy no)
                // USER + ADMIN → View products,requestmatcher for  that request checking *:product/10 1path **:prod/ss/smultiple paths
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/products/**"
                ).hasAnyRole("USER", "ADMIN")//either hasany role

                // ADMIN → Add product
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/product"
                ).hasRole("ADMIN")

                // ADMIN → Update product
                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/v1/product/**"
                ).hasRole("ADMIN")

                // ADMIN → Partial update
                .requestMatchers(
                    HttpMethod.PATCH,
                    "/api/v1/product/**"
                ).hasRole("ADMIN")

                // ADMIN → Delete product
                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/v1/product/**"
                ).hasRole("ADMIN")

                // All other requests → Login required new endpoint created it handled (if user login )
                .anyRequest().authenticated()
            )//handle permission msg 401 unauthorized
            .exceptionHandling(exception -> exception
            .accessDeniedHandler((request, response, ex) -> {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write(
                    "{\"message\":\"You do not have permission to perform this action\"}"
                );
            })
        )
//springsecurity has its own filter
.addFilterBefore(
        jwtFilter,
        UsernamePasswordAuthenticationFilter.class
);
            // HTTP Basic Authentication
           // .httpBasic(httpbasic->{});

        return http.build();
    }
}