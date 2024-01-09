package com.example.springconjwt.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.function.Function;

public interface IJwtService {

    String generateToken(UserDetails userDetails);
    String getUserEmailForToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    <T> T getClaim(String token, Function<Claims,T> claimsResolver);

}
