package com.example.springconjwt.service;

import com.example.springconjwt.dto.JwtResponse;
import com.example.springconjwt.dto.LoginRequest;
import com.example.springconjwt.dto.RegisterRequest;

public interface IAuthService {
     JwtResponse register(RegisterRequest request);
     JwtResponse login(LoginRequest request);

}
