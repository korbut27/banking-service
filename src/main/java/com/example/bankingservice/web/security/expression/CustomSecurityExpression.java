package com.example.bankingservice.web.security.expression;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.service.AccountService;
import com.example.bankingservice.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final AccountService accountService;

    public boolean canAccessUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userId.equals(id);
    }

    public boolean canAccessAccount(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Account account = accountService.getById(id);
        Long userId = user.getId();

        return account.getUser().getId().equals(userId);
    }

}
