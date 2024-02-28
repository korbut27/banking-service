package com.example.bankingservice.web.controller;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.service.AccountService;
import com.example.bankingservice.service.AuthService;
import com.example.bankingservice.service.UserService;
import com.example.bankingservice.web.dto.account.AccountDto;
import com.example.bankingservice.web.dto.auth.JwtRequest;
import com.example.bankingservice.web.dto.auth.JwtResponse;
import com.example.bankingservice.web.dto.user.UserDto;
import com.example.bankingservice.web.mappers.AccountMapper;
import com.example.bankingservice.web.mappers.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    private final AccountService accountService;

    private final UserMapper userMapper;
    private final AccountMapper accountMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody ObjectNode objectNode){
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.convertValue(objectNode.get("userDto"), UserDto.class);
        AccountDto accountDto = objectMapper.convertValue(objectNode.get("accountDto"), AccountDto.class);

        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);

        Account account = accountMapper.toEntity(accountDto);
        Account createdAccount = accountService.create(account, createdUser);

        createdUser.setAccount(createdAccount);
        return userMapper.toDto(createdUser);

    }

}
