package com.example.bankingservice.service.impl;

import com.example.bankingservice.domain.exception.ResourceNotFoundException;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.repository.UserRepository;
import com.example.bankingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        logger.debug("A user" + user + "is received by id:" + id);
        return user;
    }

    @Override
    public User getByUsername(final String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
        logger.debug("A user" + user + "is received by username:" + username);
        return user;
    }

    @Override
    @Transactional
    public User updatePhoneNumber(Long id, String phoneNumber) {
        userRepository.updatePhoneNumber(id, phoneNumber);
        logger.debug("A user with an id" + id +"has had his number changed to" + phoneNumber);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public User updateEmail(Long id, String email) {
        userRepository.updateEmail(id, email);
        logger.debug("A user with an id" + id +"has had his email changed to" + email);
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
        logger.debug("The user has been saved in the database: " + user);
        return user;
    }

    @Override
    @Transactional
    public User addPhoneNumber(Long id, String phoneNumber) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }

        userRepository.savePhoneNumber(id, phoneNumber);
        logger.debug("A number " + phoneNumber + " has been added to the user's id:" + id);
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
        logger.debug("A email " + email + " has been added to the user's id:" + id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }
        logger.debug("The user with the id:" + id + " has been deleted");
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
        logger.debug("The user with the id:" + id + " has deleted his number");
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
        logger.debug("The user with the id:" + id + " has deleted his email");
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

