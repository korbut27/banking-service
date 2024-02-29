package com.example.bankingservice.web.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ContactDataRequest {

    @Valid
    @Pattern(regexp = "^(\\+7)([0-9]{10})$",
            message = "Incorrect phone number format")
    private String phoneNumber;

    @Email(message = "Incorrect email format")
    private String email;
}
