package com.example.bankingservice.service.impl;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.exception.ResourceNotFoundException;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.repository.AccountRepository;
import com.example.bankingservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Override
    public Account getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found."));
        logger.debug("An account" + account + "is received by id:" + id);
        return account;
    }

    @Override
    @Transactional
    public Account create(BigDecimal initialDeposit, User user) {
        if (accountRepository.findByUserId(user.getId()).isPresent()) {
            throw new IllegalStateException("Account already exists.");
        }
        Account account = new Account(user, initialDeposit);
        accountRepository.save(account);
        logger.debug("The account has been saved in the database: " + account);
        return account;
    }

    @Override
    @Transactional
    public void makeTransfer(Long senderId, Long recipientId, BigDecimal amount) {
        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Account with id %d not found.", senderId)
                ));

        if (accountRepository.findById(recipientId).isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format("Account with id %d not found.", recipientId)
            );
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds.");
        }

        accountRepository.transferFunds(senderId, recipientId, amount);
        logger.debug("User with id: {} transferred funds to user with id: {}, transfer amount - {}",
                senderId, recipientId, amount);

    }

    @Override
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void increaseBalances() {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal initialDeposit = account.getInitialDeposit();

            BigDecimal newBalance = currentBalance.multiply(new BigDecimal("1.05")); // Increase balance by 5%
            BigDecimal maxBalance = initialDeposit.multiply(new BigDecimal("2.07")); // Calculate the maximum balance (207% of initial deposit)

            if (newBalance.compareTo(maxBalance) > 0) {
                newBalance = maxBalance;
            }

            account.setBalance(newBalance);
            accountRepository.save(account);
        }

        logger.debug("Client balances have been increased.");
    }

}
