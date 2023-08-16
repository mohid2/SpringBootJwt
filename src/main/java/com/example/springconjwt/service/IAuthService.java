package com.example.springconjwt.service;

import com.example.springconjwt.controller.models.AuthResponse;
import com.example.springconjwt.controller.models.LoginRequest;
import com.example.springconjwt.controller.models.RegisterRequest;

public interface IAuthService {
     AuthResponse register(RegisterRequest request);
     AuthResponse login(LoginRequest request);

}
