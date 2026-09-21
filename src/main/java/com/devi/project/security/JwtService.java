package com.devi.project.security;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {
//secert key not hard code
@Value ("${jwt.secret}")
private String secret;
    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(String username) {//jwt generate

        return Jwts.builder()//jwt build start
                .subject(username)//claims on payload sub:devi
                .signWith(secretKey)//header jjwt libary default produces->header(alg)+sceret key=sign
                .compact();//header+payload+sign=jwt token(string)
    }
    public String extractUsername(String token) {

    return Jwts.parser()//verify signature using secretKey->validate ->payload->sub

            .verifyWith(secretKey)//JWT's signature is checked using our secret key.
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
}
}