package com.example.bankingservice.web.controller;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.service.AccountService;
import com.example.bankingservice.service.UserService;
import com.example.bankingservice.web.dto.account.AccountDto;
import com.example.bankingservice.web.dto.user.UserDto;
import com.example.bankingservice.web.mappers.AccountMapper;
import com.example.bankingservice.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    private final UserMapper userMapper;
    private final AccountMapper accountMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get UserDto by id")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @GetMapping
    @Operation(summary = "Get UsersDto with filter")
    public Page<User> filterUsers(@RequestParam(required = false) LocalDate birthDate,
                                  @RequestParam(required = false) String phoneNumber,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String fullName,
                                  Pageable pageable) {
        if (birthDate != null) return userService.getUsersByBirthDate(birthDate, pageable);
        else if (phoneNumber != null) return userService.getUsersByPhoneNumber(phoneNumber, pageable);
        else if (email != null) return userService.getUsersByEmail(email, pageable);
        else if (fullName != null) return userService.getUsersByFullName(fullName, pageable);
        else return userService.getAllUsers(pageable);
    }

    @PostMapping("/{id}/account")
    @Operation(summary = "Create account")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public AccountDto createAccount(@PathVariable Long id,
                                    @PositiveOrZero(message = "Initial deposit cannot be negative.")
                                    @RequestParam BigDecimal initialDeposit) {
        User user = userService.getById(id);
        Account createdAccount = accountService.create(initialDeposit, user);
        return accountMapper.toDto(createdAccount);
    }

    @PostMapping("/{id}/phoneNumber")
    @Operation(summary = "Add phone number")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto addPhoneNumber(@PathVariable Long id,
                                  @Pattern(regexp = "^(7)([0-9]{10})$",
                                          message = "Incorrect phone number format")
                                  @RequestParam String phoneNumber) {
        User updatedUser = userService.addPhoneNumber(id, phoneNumber);
        return userMapper.toDto(updatedUser);
    }

    @PostMapping("/{id}/email")
    @Operation(summary = "Add email")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto addEmail(@PathVariable Long id,
                            @Email(message = "Incorrect email format")
                            @RequestParam String email) {
        User updatedUser = userService.addEmail(id, email);
        return userMapper.toDto(updatedUser);
    }

    @PutMapping("/{id}/phoneNumber")
    @Operation(summary = "Update phone number")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto updatePhoneNumber(@PathVariable Long id,
                                     @Pattern(regexp = "^(7)([0-9]{10})$",
                                             message = "Incorrect phone number format")
                                     @RequestParam String phoneNumber) {
        User updatedUser = userService.updatePhoneNumber(id, phoneNumber);
        return userMapper.toDto(updatedUser);
    }

    @PutMapping("/{id}/email")
    @Operation(summary = "Update email")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto updateEmail(@PathVariable Long id,
                               @Email(message = "Incorrect email format")
                               @RequestParam String email) {
        User updatedUser = userService.updateEmail(id, email);
        return userMapper.toDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @DeleteMapping("/{id}/phoneNumber")
    @Operation(summary = "Delete phone number")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deletePhoneNumber(@PathVariable Long id) {
        userService.deletePhoneNumber(id);
    }

    @DeleteMapping("/{id}/email")
    @Operation(summary = "Delete email")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteEmail(@PathVariable Long id) {
        userService.deleteEmail(id);
    }

}
