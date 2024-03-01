package com.example.bankingservice.service;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.user.User;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;

public interface AccountService {

    Account getById(Long id);

    Account create(BigDecimal initialDeposit, User user);

    void makeTransfer(Long senderId, Long recipientId, BigDecimal amount);

    @Scheduled(fixedRate = 60000)
    void increaseBalances();
}
