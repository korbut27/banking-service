package com.example.bankingservice.web.controller;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.service.AccountService;
import com.example.bankingservice.service.UserService;
import com.example.bankingservice.web.dto.account.AccountDto;
import com.example.bankingservice.web.dto.user.UserDto;
import com.example.bankingservice.web.mappers.AccountMapper;
import com.example.bankingservice.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    private final UserMapper  userMapper;
    private final AccountMapper accountMapper;

    @PutMapping
    public UserDto update(@Validated @RequestBody UserDto dto){
        User user = userMapper.toEntity(dto);
        User updateUser = userService.update(user);
        return userMapper.toDto(updateUser);
    }
    @PostMapping ("/{id}/account")
    public AccountDto createAccount(@PathVariable Long id,
                                    @Validated @RequestBody AccountDto dto){
        Account account = accountMapper.toEntity(dto);
        User user = userService.getById(id);
        Account createdAccount = accountService.create(account, user);
        return accountMapper.toDto(createdAccount);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }



}
