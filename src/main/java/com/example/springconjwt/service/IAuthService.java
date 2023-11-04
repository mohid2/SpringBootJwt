package com.example.springconjwt.service;

import com.example.springconjwt.models.AuthResponse;
import com.example.springconjwt.models.LoginRequest;
import com.example.springconjwt.models.RegisterRequest;

public interface IAuthService {
     AuthResponse register(RegisterRequest request);
     AuthResponse login(LoginRequest request);

}
