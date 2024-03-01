package com.example.bankingservice.web.dto.account;

import com.example.bankingservice.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    private BigDecimal initialDeposit;
}
