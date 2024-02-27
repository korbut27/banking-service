package com.example.bankingservice.service;

import com.example.bankingservice.domain.user.User;

public interface UserService {
    User getById(Long id);
    User getByUsername(String username);
    User update(User user);
    User create(User user);
    void delete(Long id);
}
