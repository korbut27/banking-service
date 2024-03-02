package com.example.bankingservice.web.controller;

import com.example.bankingservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Validated
@Tag(name = "Account controller", description = "Account API")
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/{id}")
    @Operation(summary = "Transfer funds")
    @PreAuthorize("@customSecurityExpression.canAccessAccount(#senderId)")
    public void transferFunds(
            @PathVariable("id") Long senderId,
            @RequestParam("recipientId") Long recipientId,
            @PositiveOrZero(message = "Funds amount cannot be negative.")
            @RequestParam("amount") BigDecimal amount
    ) {
        accountService.makeTransfer(senderId, recipientId, amount);
    }

}
