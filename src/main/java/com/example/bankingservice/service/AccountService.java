package com.example.bankingservice.service;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.user.User;

public interface AccountService {

    Account create(Account account, User user);
}
