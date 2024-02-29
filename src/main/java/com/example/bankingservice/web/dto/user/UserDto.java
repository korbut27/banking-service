package com.example.bankingservice.web.dto.user;

import com.example.bankingservice.domain.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    @NotNull(message = "Name must be not null.")
    @Length(max = 255, message = "Name length must be smaller than 255 symbols.")
    private String username;

    @NotNull(message = "Username must be not null.")
    @Length(max = 255, message = "Username length must be smaller than 255 symbols.")
    private String fullName;

    @NotNull(message = "Birth date must not be null.")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date birthDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null.")
    private String passwordConfirmation;

    private Account account;

    @NotNull(message = "Phone number must be not null.")
    @Valid
    private Set<@Pattern(regexp = "^(\\+7)([0-9]{10})$",
            message = "Incorrect phone number format") String> phoneNumbers;

    @NotNull(message = "Emails must not be empty.")
    @Valid
    private Set<@Email(message = "Incorrect email format") String> emails;
}
