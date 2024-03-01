package com.example.bankingservice.service;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.user.User;

import java.math.BigDecimal;

public interface AccountService {

    Account getById(Long id);

    Account create(Account account, User user);

    void makeTransfer(Long senderId, Long recipientId, BigDecimal amount);
}
