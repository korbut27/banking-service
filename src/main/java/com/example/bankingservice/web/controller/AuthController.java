package com.example.bankingservice.web.controller;

import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.service.AuthService;
import com.example.bankingservice.service.UserService;
import com.example.bankingservice.web.dto.auth.JwtRequest;
import com.example.bankingservice.web.dto.auth.JwtResponse;
import com.example.bankingservice.web.dto.user.UserDto;
import com.example.bankingservice.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);

    }

}
