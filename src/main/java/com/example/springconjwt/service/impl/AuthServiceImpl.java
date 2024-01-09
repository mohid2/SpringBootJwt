package com.example.springconjwt.service.impl;

import com.example.springconjwt.dto.JwtResponse;
import com.example.springconjwt.dto.LoginRequest;
import com.example.springconjwt.dto.RegisterRequest;
import com.example.springconjwt.entity.Role;
import com.example.springconjwt.entity.User;
import com.example.springconjwt.repository.IUserRepository;
import com.example.springconjwt.security.service.impl.JwtServiceImpl;
import com.example.springconjwt.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtResponse register(RegisterRequest request) {
        if(!userRepository.findByEmail(request.getEmail()).isPresent()){
            User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            String jwtToken= jwtServiceImpl.generateToken(user);
            return JwtResponse.builder().message("Se ha registrado con exito").token(jwtToken).build();
        }
        return JwtResponse.builder().message("El user esta duplicado").build();
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String jwtToken= jwtServiceImpl.generateToken(user);
            return JwtResponse.builder().message("Se ha autenticado con exitó").token(jwtToken).build();

    }
}
