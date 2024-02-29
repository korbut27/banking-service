package com.example.bankingservice.web.controller;

import com.example.bankingservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#senderId)")
    public void transferFunds(
            @PathVariable("id") Long senderId,
            @RequestParam("recipientId") Long recipientId,
            @RequestParam("amount") BigDecimal amount
    ) {
        accountService.makeTransfer(senderId, recipientId, amount);
    }

}
