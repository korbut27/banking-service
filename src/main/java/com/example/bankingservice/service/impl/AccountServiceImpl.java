package com.example.bankingservice.service.impl;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.repository.AccountRepository;
import com.example.bankingservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Account create(Account account, User user) {
        account.setUser(user);
        accountRepository.save(account);
        return account;
    }
}
