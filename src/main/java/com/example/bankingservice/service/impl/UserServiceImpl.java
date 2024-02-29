package com.example.bankingservice.service.impl;

import com.example.bankingservice.domain.exception.ResourceNotFoundException;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.repository.UserRepository;
import com.example.bankingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    public User getByUsername(
            final String username
    ) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public User updatePhoneNumber(Long id, String phoneNumber) {
        userRepository.updatePhoneNumber(id, phoneNumber);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public User updateEmail(Long id, String email) {
        userRepository.updateEmail(id, email);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException(
                    "Password and password confirmation do not match."
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User addPhoneNumber(Long id, String phoneNumber) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }

        userRepository.savePhoneNumber(id, phoneNumber);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public User addEmail(Long id, String email) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }
        userRepository.saveEmail(id, email);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deletePhoneNumber(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (user.getPhoneNumbers().size() == 1) {
            throw new IllegalStateException(
                    "Unable to delete the last phone number."
            );
        }

        userRepository.deletePhoneNumber(id);
    }

    @Override
    @Transactional
    public void deleteEmail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (user.getPhoneNumbers().size() == 1) {
            throw new IllegalStateException(
                    "Unable to delete the last email."
            );
        }
        userRepository.deleteEmail(id);
    }

    @Override
    public Page<User> getUsersByBirthDate(LocalDate birthDate, Pageable pageable) {
        Timestamp timestamp = Timestamp.valueOf(birthDate.atStartOfDay());
        return userRepository.findByBirthDate(timestamp, pageable);
    }

    @Override
    public Page<User> getUsersByPhoneNumber(String phoneNumber, Pageable pageable) {
        return userRepository.findByPhoneNumber(phoneNumber, pageable);
    }

    @Override
    public Page<User> getUsersByEmail(String email, Pageable pageable) {
        return userRepository.findByEmail(email, pageable);
    }

    @Override
    public Page<User> getUsersByFullName(String fullName, Pageable pageable) {
        return userRepository.findByFullNameLike(fullName, pageable);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}

