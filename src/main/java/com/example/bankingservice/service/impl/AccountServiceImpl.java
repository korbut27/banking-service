package com.example.bankingservice.service.impl;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.exception.ResourceNotFoundException;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.repository.AccountRepository;
import com.example.bankingservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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

    @Override
    @Transactional
    public void makeTransfer(Long senderId, Long recipientId, BigDecimal amount) {
        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Account with id %d not found.", senderId)
                ));

        if (accountRepository.findById(senderId).isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format("Account with id %d not found.", recipientId)
            );
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds.");
        }

        accountRepository.transferFunds(senderId, recipientId, amount);
    }
}
