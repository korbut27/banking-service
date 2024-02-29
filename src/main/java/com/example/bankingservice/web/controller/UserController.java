package com.example.bankingservice.web.controller;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.service.AccountService;
import com.example.bankingservice.service.UserService;
import com.example.bankingservice.web.dto.account.AccountDto;
import com.example.bankingservice.web.dto.user.ContactDataRequest;
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

    private final UserMapper userMapper;
    private final AccountMapper accountMapper;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @PostMapping("/{id}/account")
    public AccountDto createAccount(@PathVariable Long id,
                                    @Validated @RequestBody AccountDto dto) {
        Account account = accountMapper.toEntity(dto);
        User user = userService.getById(id);
        Account createdAccount = accountService.create(account, user);
        return accountMapper.toDto(createdAccount);
    }

    @PostMapping("/{id}/phoneNumber")
    public UserDto addPhoneNumber(@PathVariable Long id,
                                  @Validated @RequestBody ContactDataRequest request) {
        User updatedUser = userService.addPhoneNumber(id, request.getPhoneNumber());
        return userMapper.toDto(updatedUser);
    }

    @PostMapping("/{id}/email")
    public UserDto addEmail(@PathVariable Long id,
                            @Validated @RequestBody ContactDataRequest request) {
        User updatedUser = userService.addEmail(id, request.getEmail());
        return userMapper.toDto(updatedUser);
    }

    @PutMapping("/{id}/phoneNumber")
    public UserDto updatePhoneNumber(@PathVariable Long id,
                                     @Validated @RequestBody ContactDataRequest request) {
        User updatedUser = userService.updatePhoneNumber(id, request.getPhoneNumber());
        return userMapper.toDto(updatedUser);
    }

    @PutMapping("/{id}/email")
    public UserDto updateEmail(@PathVariable Long id,
                               @Validated @RequestBody ContactDataRequest request) {
        User updatedUser = userService.updateEmail(id, request.getEmail());
        return userMapper.toDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @DeleteMapping("/{id}/phoneNumber")
    public void deletePhoneNumber(@PathVariable Long id) {
        userService.deletePhoneNumber(id);
    }

    @DeleteMapping("/{id}/email")
    public void deleteEmail(@PathVariable Long id) {
        userService.deleteEmail(id);
    }

}
