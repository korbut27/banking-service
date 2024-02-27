package com.example.bankingservice.service;

import com.example.bankingservice.web.dto.auth.JwtRequest;
import com.example.bankingservice.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
