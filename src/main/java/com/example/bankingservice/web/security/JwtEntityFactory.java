package com.example.bankingservice.web.security;

import com.example.bankingservice.domain.user.User;

public final class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getPassword()
        );
    }

}