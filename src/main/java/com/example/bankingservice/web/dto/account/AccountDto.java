package com.example.bankingservice.web.dto.account;

import com.example.bankingservice.domain.user.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Long id;

    private User user;

    private BigDecimal balance;
}
