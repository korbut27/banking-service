package com.example.bankingservice.service;

import com.example.bankingservice.domain.user.User;

public interface UserService {
    User getById(Long id);

    User getByUsername(String username);

    User create(User user);

    User addPhoneNumber(Long id, String phoneNumber);

    User addEmail(Long id, String email);

    User updatePhoneNumber(Long id, String phoneNumber);

    User updateEmail(Long id, String email);

    void delete(Long id);

    void deletePhoneNumber(Long id);

    void deleteEmail(Long id);
}
