package com.example.bankingservice.service;

import com.example.bankingservice.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

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

    Page<User> getUsersByBirthDate(LocalDate birthDate, Pageable pageable);

    Page<User> getUsersByPhoneNumber(String phoneNumber, Pageable pageable);

    Page<User> getUsersByEmail(String email, Pageable pageable);

    Page<User> getUsersByFullName(String fullName, Pageable pageable);

    Page<User> getAllUsers(Pageable pageable);
}
