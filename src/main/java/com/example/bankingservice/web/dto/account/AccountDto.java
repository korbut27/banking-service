package com.example.bankingservice.web.dto.account;

import com.example.bankingservice.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @PositiveOrZero(message = "Initial deposit cannot be negative.")
    private BigDecimal initialDeposit;
}
